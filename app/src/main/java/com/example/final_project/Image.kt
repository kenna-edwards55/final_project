package com.example.final_project

import com.google.firebase.firestore.PropertyName

/**
 * Data class representing a Post in the application.
 *
 * @property fileName The name of the file associated with the post.
 * @property imageUrl The URL of the image associated with the post.
 * @property creationTimeMs The creation time of the post in milliseconds.
 * @property user The user associated with the post. It can be null if no user is associated.
 */
data class Image(
    var fileName: String = "",
    @get:PropertyName("image_url") @set:PropertyName("image_url")
    var imageUrl: String = "",
)
