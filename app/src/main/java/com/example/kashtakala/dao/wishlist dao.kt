package com.example.kashtakala.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kashtakala.model.Wishlist

@Dao
interface WishlistDao {
    @Query("SELECT * FROM wishlist WHERE userId = :userId")
    suspend fun getWishlistByUser(userId: Int): List<Wishlist>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToWishlist(wishlist: Wishlist)

    @Delete
    suspend fun removeFromWishlist(wishlist: Wishlist)

    @Query("SELECT EXISTS(SELECT * FROM wishlist WHERE userId = :userId AND productId = :productId)")
    suspend fun isInWishlist(userId: Int, productId: Int): Boolean
}