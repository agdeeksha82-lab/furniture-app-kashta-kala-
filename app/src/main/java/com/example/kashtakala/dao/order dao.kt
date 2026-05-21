package com.example.kashtakala.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kashtakala.model.Order

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order)

    @Query("SELECT * FROM orders ORDER BY id DESC")
    suspend fun getAllOrders(): List<Order>

    @Query("DELETE FROM orders")
    suspend fun deleteAllOrders()
}