package com.begmyratmammedov.finalmanagement.model

data class Sale(
    val date: Long,     // Store date as timestamp
    val itemId: String, // ID of the sold item
    val quantity: Int   // Quantity sold
)
