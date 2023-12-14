package com.example.final_project

/**
 * Data class representing a user's credentials, including email and password.
 *
 * @property email The user's email address.
 * @property password The user's password.
 */
data class User(
    var email: String = "",
    var password: String = "testing123",
    var name: String= ""
)
