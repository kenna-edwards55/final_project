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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.R

/**
 * ViewModel class for managing orders and navigation in the RecentOrdersFragment.
 *
 */

class OrdersViewModel() : ViewModel() {
    val TAG = "OrdersViewModel"

    /**
     * Firebase auth. Used with Google Firebase to authenticate user.
     */
    private var auth: FirebaseAuth

    /**
     * [user]: User- User object for authentication.
     * [verifyPassword]: String - Password verification for user registration.
     */
    var user: User = User()
    var verifyPassword = "testing123"

    /**
     * Unique identifier for the currently edited order.
     */
    var orderId: String = ""

    /**
     * Keeps track of the firebase auth user currently logged in.
     */
    private var currentUser = MutableLiveData<FirebaseUser?>()

    private val _currentUserData = MutableLiveData<User>()

    // Expose an immutable LiveData to the UI
    val currentUserData: LiveData<User>
        get() = _currentUserData


    /**
     * Boolean that keeps track of when a user is logged in.  Affects which options are available in UserScreen
     */
    var loggedIn = MutableLiveData<Boolean>(false)

    /**
     * LiveData representing the currently edited order.
     */
    var order = MutableLiveData<Order>()

    /**
     * LiveData representing a list of orders.
     */
    private val _orders: MutableLiveData<MutableList<Order>> = MutableLiveData()
    val orders: LiveData<List<Order>>
        get() = _orders as LiveData<List<Order>>

    /**
     * LiveData for posts
     */
    private val _images: MutableLiveData<MutableList<Image>> = MutableLiveData()
    val images: LiveData<List<Image>>
        get() = _images as LiveData<List<Image>>


    /**
     * LiveData representing the currently edited order.
     */
    var restaurant = MutableLiveData<Restaurant>()


    /**
     * LiveData representing a list of orders.
     */
    private val _restaurants: MutableLiveData<MutableList<Restaurant>> = MutableLiveData()
    val restaurants: LiveData<List<Restaurant>>
        get() = _restaurants as LiveData<List<Restaurant>>

    private val _selectedRestaurantId = MutableLiveData<String>()

//    val selectedRestaurantMenuItems: LiveData<List<MenuItem>> =
//        Transformations.switchMap(_selectedRestaurantId) { restaurantId ->
//            MutableLiveData<List<MenuItem>>().apply {
//                // Load menu items based on the selected restaurant ID
//                // This is where you should fetch menu items from your database
//                // and update the value of this MutableLiveData
//                // For simplicity, let's assume you have a function called `loadMenuItems`
//                // that fetches menu items based on the restaurant ID
//                loadMenuItems(restaurantId)
//            }
//        }


    /**
     * LiveData to indicate navigation to a specific order.
     */
    private val _navigateToOrder = MutableLiveData<String?>()

    /**
     * LiveData representing the order to navigate to.
     */
    val navigateToOrder: LiveData<String?>
        get() = _navigateToOrder

    /**
     * LiveData for displaying error messages.
     */

    private val _errorHappened = MutableLiveData<String?>()
    val errorHappened: LiveData<String?>
        get() = _errorHappened

    /**
     * LiveData for indicating navigation to the order list.
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

    private val _menuItems = MutableLiveData<List<MenuItem>>()

    // Expose menuItems as LiveData to observe changes
    val menuItems: LiveData<List<MenuItem>> get() = _menuItems

    // Populate menuItems with data from _restaurant


    /**
     * Keeps track of data changing within order objects and communicated with firebase realtime DB.
     */
    private lateinit var ordersCollection: DatabaseReference
    private lateinit var restaurantsCollection: DatabaseReference
    private val database = Firebase.database

    init {
        auth = Firebase.auth
        if (orderId.trim() == "") {
            order.value = Order()
        }
        _orders.value = mutableListOf<Order>()
        _restaurants.value= mutableListOf<Restaurant>()

        _images.value = mutableListOf<Image>()

        val firestoreDB = FirebaseFirestore.getInstance()

        /**
         * Fetch the currently signed in user from Firestore
         */
        firestoreDB.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapshot ->
                user = userSnapshot.toObject<User>()!!
                Log.i(TAG, "signed in user: $user")
            }
            .addOnFailureListener { exception ->
                Log.i(TAG, "Failure fetching signed in user", exception)
            }

        /**
         * Query posts from Firestore and listen for changes
         */
        var imagesReference = firestoreDB
            .collection("images")
            .limit(30)

        imagesReference.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) {
                Log.e(TAG, "Exception when querying images", exception)
                return@addSnapshotListener
            }
            val imageList = snapshot.toObjects<Image>()
            _images.value = imageList as MutableList<Image>
            for (image in imageList) {
                Log.i(TAG, "Image ${image.fileName}")
            }
        }
    }

    /**
     * Initializes the Firebase database reference for orders.
     */
    fun initializeTheDatabaseReference() {
        ordersCollection = database
            .getReference("orders")
            .child(auth.currentUser!!.uid)
        restaurantsCollection = database
            .getReference("restaurants")
            .child(auth.currentUser!!.uid)

        ordersCollection.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var ordersList: ArrayList<Order> = ArrayList()
                for (orderSnapshot in dataSnapshot.children) {
                    var order = orderSnapshot.getValue<Order>()
                    order?.orderId = orderSnapshot.key!!
                    ordersList.add(order!!)
                }
                _orders.value = ordersList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                // ...
            }
        })

        restaurantsCollection.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var restaurantsList: ArrayList<Restaurant> = ArrayList()
                for (restaurantSnapshot in dataSnapshot.children) {
                    var restaurant: Restaurant? = restaurantSnapshot.getValue<Restaurant>()
                    restaurant?.restaurantId = restaurantSnapshot.key!!
                    restaurantsList.add(restaurant!!)
                }
                _restaurants.value = restaurantsList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                // ...
            }
        })
    }


//    val menuItemsAdapter = MutableLiveData<MenuItemAdapter>()

    // ... other methods ...

    // Update the adapter when needed
//    fun updateOrderItemsAdapter(order: Order) {
//        // Assuming OrderItemsAdapter is your RecyclerView adapter class
//        val adapter = MenuItemAdapter(order.orderItems)
//        menuItemsAdapter.value = adapter
//    }

    /**
     * Adds a new, empty order to the database and navigates to the newly created order.
     */
    fun addOrder() {
        Log.d(TAG, "Adding a order")
        _navigateToOrder.value = ""
        orderId = ""
        order.value = Order()
    }


    fun addRestaurantToDatabase(newRestaurant: Restaurant) {
        Log.d("ViewModel", "Inside add restaurant")

        // Assuming restaurantsCollection is a DatabaseReference
        restaurantsCollection.push().setValue(newRestaurant)
            .addOnCompleteListener { task ->
//                Log.d("Order View Model")
                if (task.isSuccessful) {
                    Log.d("View Model", "Restaurant added")
                    // Restaurant added successfully
                    // Handle success logic if needed
                } else {
                    Log.e("View Model", "Error adding restaurant: ${task.exception}")
                    // Error adding restaurant
                    // Handle failure logic if needed
                }
            }
    }

    fun addOrderToDatabase(order: Order) {
        Log.d("ViewModel", "Inside add order")

        ordersCollection.push().setValue(order)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("View Model", "Order added")
                } else {
                    Log.e("View Model", "Error adding Order: ${task.exception}")
                }
            }
    }


    /**
     * Deletes a order with the specified order ID from the database.
     *
     * @param orderId The unique identifier of the order to be deleted.
     */
    fun deleteOrder(orderId: String) {
        Log.d(TAG, "Deleting order with ID: $orderId")

        ordersCollection.child(orderId.toString()).removeValue()
        _navigateToList.value = true
    }

    /**
     * Updates the order in the database and triggers navigation to the order list.
     */
    fun updateOrder() {
        Log.d(TAG, "Updating the order.")

        if (orderId.trim() == "") {
            ordersCollection.push().setValue(order.value).addOnSuccessListener {
                Log.d(TAG, "Successfully added order to the DB")
            }
                .addOnFailureListener { e ->
                    Log.e(TAG, e.toString())
                }
        } else {
            ordersCollection.child(orderId).setValue(order.value).addOnSuccessListener {
                // Data has been successfully posted
            }
                .addOnFailureListener { e ->
                    Log.e(TAG, e.toString())
                }
        }
        _navigateToList.value = true
    }


    /**
     * Handles a order item click event by setting the value of [navigateToOrder] LiveData.
     *
     * @param orderId The unique identifier of the clicked order.
     */
    fun onOrderClicked(selectedOrder: Order) {
        _navigateToOrder.value = selectedOrder.orderId
        orderId = selectedOrder.orderId
        order.value = selectedOrder

        Log.d(TAG, "Order clicked with ID: $orderId")
    }

    /**
     * Resets the [navigateToXYZ] LiveData to null after navigating to a desired endpoint
     */
    fun onOrderNavigated() {
        _navigateToOrder.value = null
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
     * the database reference is initialized, and the user is marked as logged in with navigation to the order list.
     * If the sign-in fails, an error message is displayed.
     */
    fun signIn() {
        if (user.email.isEmpty() ) { //|| user.password.isEmpty()
            _errorHappened.value = "Email and password cannot be empty."
            return
        }
        auth.signInWithEmailAndPassword(user.email, user.password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "Login was successful")
                initializeTheDatabaseReference()
                currentUser.value = getCurrentUser()

                updateUser(user)
//                currentUserName.value = user.name
//                currentUserEmail.value = user.email
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
    //TODO change things related to password
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

    fun updateUser(user: User) {
        _currentUserData.value = user
    }


}