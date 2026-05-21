package com.example.kashtakala.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kashtakala.dao.CartDao
import com.example.kashtakala.dao.FeedbackDao
import com.example.kashtakala.dao.OrderDao
import com.example.kashtakala.dao.ProductDao
import com.example.kashtakala.dao.UserDao
import com.example.kashtakala.dao.WishlistDao
import com.example.kashtakala.model.Cart
import com.example.kashtakala.model.Feedback
import com.example.kashtakala.model.Order
import com.example.kashtakala.model.Product
import com.example.kashtakala.model.User
import com.example.kashtakala.model.Wishlist

@Database(entities = [User::class, Product::class, Wishlist::class, Cart::class, Feedback::class, Order::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun wishlistDao(): WishlistDao
    abstract fun cartDao(): CartDao
    abstract fun feedbackDao(): FeedbackDao
    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "kashtakala_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}