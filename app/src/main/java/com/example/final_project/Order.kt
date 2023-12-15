package com.example.final_project

import com.google.firebase.database.Exclude
import java.sql.Timestamp

/**
 * Data class representing a Order in the Firebase database.
 *
 * @property orderId The unique identifier for the order. Excluded from serialization.
 * @property orderRestaurantName The name of the order.
 * @property orderDescription The description or content of the order.
 */

data class Order(
    @get:Exclude
    var orderId:String = "",
    var orderRestaurantName: String = "",
    var orderImageCode: String = "",
    var orderItems: List<MenuItem>? = null,
    var orderDeliveryAddress: String = "",
    var orderSpecialInstructions: String = "",
    var timestamp: Timestamp? = null,
//    var orderDescription: String = ""
)


