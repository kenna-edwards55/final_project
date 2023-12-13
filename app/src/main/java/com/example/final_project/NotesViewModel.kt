package com.example.final_project

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue

/**
 * ViewModel class for managing notes and navigation in the NotesFragment.
 *
 */

class NotesViewModel() : ViewModel() {
    val TAG = "NotesViewModel"

    /**
     * Firebase auth. Used with Google Firebase to authenticate user.
     */
    private var auth: FirebaseAuth

    /**
     * [user]: User- User object for authentication.
     * [verifyPassword]: String - Password verification for user registration.
     */
    var user: User = User()
    var verifyPassword = ""

    /**
     * Unique identifier for the currently edited note.
     */
    var noteId: String = ""

    /**
     * Keeps track of the firebase auth user currently logged in.
     */
    private var currentUser = MutableLiveData<FirebaseUser?>()

    /**
     * Boolean that keeps track of when a user is logged in.  Affects which options are available in UserScreen
     */
    var loggedIn = MutableLiveData<Boolean>(false)

    /**
     * LiveData representing the currently edited note.
     */
    var note = MutableLiveData<Note>()

    /**
     * LiveData representing a list of notes.
     */
    private val _notes: MutableLiveData<MutableList<Note>> = MutableLiveData()
    val notes: LiveData<List<Note>>
        get() = _notes as LiveData<List<Note>>

    /**
     * LiveData to indicate navigation to a specific note.
     */
    private val _navigateToNote = MutableLiveData<String?>()

    /**
     * LiveData representing the note to navigate to.
     */
    val navigateToNote: LiveData<String?>
        get() = _navigateToNote

    /**
     * LiveData for displaying error messages.
     */

    private val _errorHappened = MutableLiveData<String?>()
    val errorHappened: LiveData<String?>
        get() = _errorHappened

    /**
     * LiveData for indicating navigation to the note list.
     */
    private val _navigateToList = MutableLiveData<Boolean>(false)
    val navigateToList: LiveData<Boolean>
        get() = _navigateToList

    /**
     * LiveData for indicating navigation to the sign-up screen.
     */
    private val _navigateToSignUp = MutableLiveData<Boolean>(false)
    val navigateToSignUp: LiveData<Boolean>
        get() = _navigateToSignUp

    /**
     * LiveData for indicating navigation to the sign-in screen.
     */
    private val _navigateToSignIn = MutableLiveData<Boolean>(false)
    val navigateToSignIn: LiveData<Boolean>
        get() = _navigateToSignIn

    /**
     * Keeps track of data changing within note objects and communicated with firebase realtime DB.
     */
    private lateinit var notesCollection: DatabaseReference
    private val database = Firebase.database

    init {
        auth = Firebase.auth
        if (noteId.trim() == "") {
            note.value = Note()
        }
        _notes.value = mutableListOf<Note>()
    }

    /**
     * Initializes the Firebase database reference for notes.
     */
    fun initializeTheDatabaseReference() {
        notesCollection = database
            .getReference("notes")
            .child(auth.currentUser!!.uid)

        notesCollection.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var notesList: ArrayList<Note> = ArrayList()
                for (noteSnapshot in dataSnapshot.children) {
                    var note = noteSnapshot.getValue<Note>()
                    note?.noteId = noteSnapshot.key!!
                    notesList.add(note!!)
                }
                _notes.value = notesList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                // ...
            }
        })

    }

    /**
     * Adds a new, empty note to the database and navigates to the newly created note.
     */
    fun addNote() {
        Log.d(TAG, "Adding a note")
        _navigateToNote.value = ""
        noteId = ""
        note.value = Note()
    }

    /**
     * Deletes a note with the specified note ID from the database.
     *
     * @param noteId The unique identifier of the note to be deleted.
     */
    fun deleteNote(noteId: String) {
        Log.d(TAG, "Deleting note with ID: $noteId")

        notesCollection.child(noteId.toString()).removeValue()
        _navigateToList.value = true
    }

    /**
     * Updates the note in the database and triggers navigation to the note list.
     */
    fun updateNote() {
        Log.d(TAG, "Updating the note.")

        if (noteId.trim() == "") {
            notesCollection.push().setValue(note.value).addOnSuccessListener {
                Log.d(TAG, "Successfully added note to the DB")
            }
                .addOnFailureListener { e ->
                    Log.e(TAG, e.toString())
                }
        } else {
            notesCollection.child(noteId).setValue(note.value).addOnSuccessListener {
                // Data has been successfully posted
            }
                .addOnFailureListener { e ->
                    Log.e(TAG, e.toString())
                }
        }
        _navigateToList.value = true
    }

    /**
     * Handles a note item click event by setting the value of [navigateToNote] LiveData.
     *
     * @param noteId The unique identifier of the clicked note.
     */
    fun onNoteClicked(selectedNote: Note) {
        _navigateToNote.value = selectedNote.noteId
        noteId = selectedNote.noteId
        note.value = selectedNote

        Log.d(TAG, "Note clicked with ID: $noteId")
    }

    /**
     * Resets the [navigateToXYZ] LiveData to null after navigating to a desired endpoint
     */
    fun onNoteNavigated() {
        _navigateToNote.value = null
    }

    fun onNavigatedToList() {
        _navigateToList.value = false
    }

    fun onNavigatedToSignUp() {
        _navigateToSignUp.value = false
    }

    fun onNavigatedToSignIn() {
        _navigateToSignIn.value = false
    }

    fun navigateToSignUp() {
        _navigateToSignUp.value = true
    }

    /**
     * Attempts to sign in the user with the provided email and password.
     *
     * If the email or password is empty, an error message is set. If the sign-in is successful,
     * the database reference is initialized, and the user is marked as logged in with navigation to the note list.
     * If the sign-in fails, an error message is displayed.
     */
    fun signIn() {
        if (user.email.isEmpty() || user.password.isEmpty()) {
            _errorHappened.value = "Email and password cannot be empty."
            return
        }
        auth.signInWithEmailAndPassword(user.email, user.password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "Login was successful")
                initializeTheDatabaseReference()
                currentUser.value = getCurrentUser()
                loggedIn.value = true
                _navigateToList.value = true
            } else {
                _errorHappened.value = it.exception?.message
            }
        }
    }

    /**
     * Attempts to sign up the user with the provided email and password.
     *
     * If the email or password is empty, an error message is set. If the passwords do not match,
     * an error message is displayed. If the sign-up is successful, navigation to the sign-in screen is initiated.
     * If the sign-up fails, an error message is shown.
     */
    fun signUp() {
        if (user.email.isEmpty() || user.password.isEmpty()) {
            _errorHappened.value = "Email and password cannot be empty."
            return
        }
        if (user.password != verifyPassword) {
            _errorHappened.value = "Password and verify do not match."
            return
        }
        auth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener {
            if (it.isSuccessful) {
                _navigateToSignIn.value = true
            } else {
                _errorHappened.value = it.exception?.message
            }
        }
    }

    /**
     * Signs the user out, sets the user as not logged in, and navigates to the sign-in screen.
     */
    fun signOut() {
        auth.signOut()
        currentUser.value = null
        loggedIn.value = false
        _navigateToSignIn.value = true
    }

    /**
     * Retrieves the currently authenticated user from Firebase authentication.
     *
     * @return The currently authenticated user or null if not authenticated.
     */
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
}