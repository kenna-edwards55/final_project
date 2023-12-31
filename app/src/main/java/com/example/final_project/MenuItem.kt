package com.example.final_project

import com.google.firebase.database.Exclude

/**
 * Data class representing a Order in the Firebase database.
 *
 * @property orderId The unique identifier for the order. Excluded from serialization.
 * @property orderName The name of the order.
 * @property orderDescription The description or content of the order.
 */

data class MenuItem(
    @get:Exclude
    var itemId:String = "",
    var itemName: String = "",
    var itemCost: String = "",
    var itemQuantity: String = ""
)
