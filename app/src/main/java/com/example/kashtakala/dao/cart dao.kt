package com.example.kashtakala.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kashtakala.model.Cart

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: Cart)

    @Query("SELECT * FROM cart")
    suspend fun getCartItems(): List<Cart>

    @Query("DELETE FROM cart")
    suspend fun clearCart()

    @Query("DELETE FROM cart WHERE id = :id")
    suspend fun deleteCartItem(id: Int)
}