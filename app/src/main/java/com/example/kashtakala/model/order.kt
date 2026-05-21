package com.example.kashtakala.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productName: String,
    val productPrice: Int,
    val quantity: Int,
    val totalPrice: Int,
    val paymentMethod: String,
    val orderDate: String
)