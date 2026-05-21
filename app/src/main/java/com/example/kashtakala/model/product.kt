package com.example.kashtakala.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val image: Int,
    val price: Int,
    val category: String,
    val description: String = "Crafted from premium wood, this elegant furniture piece brings luxury and comfort to your home."
)