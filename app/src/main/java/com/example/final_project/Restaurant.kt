package com.example.final_project

import com.google.firebase.database.Exclude

/**
 * Data class representing a Order in the Firebase database.
 *
 * @property orderId The unique identifier for the order. Excluded from serialization.
 * @property orderRestaurantName The name of the order.
 * @property orderDescription The description or content of the order.
 */

data class Restaurant(
    @get:Exclude
    var restaurantId:String = "",
    var restaurantImageCode: String = "",
    var restaurantName: String = "",
    var imageUrls: List<String>? = null,
    var menu: List<MenuItem>? = null,
    var restaurantAddress: String = "",
)


