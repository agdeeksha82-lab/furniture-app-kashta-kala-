package com.example.kashtakala.activities

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.kashtakala.R
import com.example.kashtakala.database.AppDatabase
import com.example.kashtakala.model.Cart
import com.example.kashtakala.model.Wishlist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val productImage = findViewById<ImageView>(R.id.detailProductImage)
        val productName = findViewById<TextView>(R.id.detailProductName)
        val productPrice = findViewById<TextView>(R.id.detailProductPrice)
        val productDescription = findViewById<TextView>(R.id.detailProductDescription)
        val addToCartBtn = findViewById<Button>(R.id.detailAddToCartBtn)
        val wishlistBtn = findViewById<ImageButton>(R.id.detailWishlistBtn)

        val id = intent.getIntExtra("id", -1)
        val name = intent.getStringExtra("name") ?: ""
        val price = intent.getIntExtra("price", 0)
        val image = intent.getIntExtra("image", R.mipmap.ic_launcher)
        val description = intent.getStringExtra("description") ?: "Luxury wooden furniture piece."

        productName.text = name
        productPrice.text = "₹$price"
        productImage.setImageResource(image)
        productDescription.text = description

        val database = AppDatabase.getDatabase(this)

        addToCartBtn.setOnClickListener {
            lifecycleScope.launch {
                val cartItem = Cart(name = name, price = price, image = image)
                withContext(Dispatchers.IO) {
                    database.cartDao().insertCart(cartItem)
                }
                Toast.makeText(this@ProductDetailActivity, "Added to Cart", Toast.LENGTH_SHORT).show()
            }
        }

        wishlistBtn.setOnClickListener {
            lifecycleScope.launch {
                val wishlistEntry = Wishlist(
                    userId = 1,
                    productId = id,
                    productName = name,
                    productImage = image,
                    productPrice = price
                )
                withContext(Dispatchers.IO) {
                    database.wishlistDao().addToWishlist(wishlistEntry)
                }
                Toast.makeText(this@ProductDetailActivity, "Added to Wishlist", Toast.LENGTH_SHORT).show()
            }
        }
    }
}