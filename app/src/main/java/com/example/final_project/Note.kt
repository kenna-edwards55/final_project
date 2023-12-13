package com.example.final_project

import com.google.firebase.database.Exclude

/**
 * Data class representing a Note in the Firebase database.
 *
 * @property noteId The unique identifier for the note. Excluded from serialization.
 * @property noteName The name of the note.
 * @property noteDescription The description or content of the note.
 */

data class Note(
    @get:Exclude
    var noteId:String = "",
    var noteName: String = "",
    var noteDescription: String = ""
)
