package com.example.kashtakala.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feedback")
data class Feedback(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productName: String,
    val rating: Float,
    val review: String,
    val timestamp: Long = System.currentTimeMillis()
)