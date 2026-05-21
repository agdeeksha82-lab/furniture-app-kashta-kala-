package com.example.kashtakala.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kashtakala.R
import com.example.kashtakala.activities.ProductDetailActivity
import com.example.kashtakala.adapters.CategoryAdapter
import com.example.kashtakala.adapters.ProductAdapter
import com.example.kashtakala.database.AppDatabase
import com.example.kashtakala.model.Cart
import com.example.kashtakala.model.Category
import com.example.kashtakala.model.Product
import com.example.kashtakala.model.Wishlist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var productRv: RecyclerView
    private lateinit var categoryRv: RecyclerView
    private lateinit var database: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        productRv = view.findViewById(R.id.recyclerView)
        categoryRv = view.findViewById(R.id.categoryRv)
        
        productRv.layoutManager = GridLayoutManager(context, 2)
        categoryRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        database = AppDatabase.getDatabase(requireContext())

        loadCategories()
        loadProducts()

        return view
    }

    private fun loadCategories() {
        val categories = listOf(
            Category("Sofa", R.mipmap.ic_launcher),
            Category("Table", R.mipmap.ic_launcher),
            Category("Bed", R.mipmap.ic_launcher),
            Category("Chair", R.mipmap.ic_launcher),
            Category("Storage", R.mipmap.ic_launcher)
        )
        categoryRv.adapter = CategoryAdapter(categories) { category ->
            Toast.makeText(requireContext(), "Selected: ${category.name}", Toast.LENGTH_SHORT).show()
            // Here you could filter products by category
        }
    }

    private fun loadProducts() {
        viewLifecycleOwner.lifecycleScope.launch {
            var productList = database.productDao().getAllProducts()
            
            if (productList.isEmpty()) {
                addSampleData()
                productList = database.productDao().getAllProducts()
            }
            
            productRv.adapter = ProductAdapter(
                productList,
                onCartClick = { product -> addToCart(product) },
                onWishlistClick = { product -> addToWishlist(product) },
                onProductClick = { product -> openProductDetail(product) }
            )
        }
    }

    private fun openProductDetail(product: Product) {
        val intent = Intent(requireContext(), ProductDetailActivity::class.java).apply {
            putExtra("id", product.id)
            putExtra("name", product.name)
            putExtra("price", product.price)
            putExtra("image", product.image)
            putExtra("description", product.description)
        }
        startActivity(intent)
    }

    private fun addToCart(product: Product) {
        lifecycleScope.launch {
            val cartItem = Cart(name = product.name, price = product.price, image = product.image)
            withContext(Dispatchers.IO) {
                database.cartDao().insertCart(cartItem)
            }
            Toast.makeText(requireContext(), "${product.name} added to cart", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addToWishlist(product: Product) {
        lifecycleScope.launch {
            val wishlistEntry = Wishlist(
                userId = 1,
                productId = product.id,
                productName = product.name,
                productImage = product.image,
                productPrice = product.price
            )
            withContext(Dispatchers.IO) {
                database.wishlistDao().addToWishlist(wishlistEntry)
            }
            Toast.makeText(requireContext(), "Saved to wishlist", Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun addSampleData() {
        val samples = listOf(
            Product(name = "Teakwood Sofa", image = R.mipmap.ic_launcher, price = 45000, category = "Sofa"),
            Product(name = "Oak Dining Table", image = R.mipmap.ic_launcher, price = 32000, category = "Table"),
            Product(name = "Royal King Bed", image = R.mipmap.ic_launcher, price = 55000, category = "Bed"),
            Product(name = "Modern Armchair", image = R.mipmap.ic_launcher, price = 12000, category = "Chair"),
            Product(name = "Wooden Bookshelf", image = R.mipmap.ic_launcher, price = 8500, category = "Storage"),
            Product(name = "Coffee Table", image = R.mipmap.ic_launcher, price = 4500, category = "Table")
        )
        samples.forEach { database.productDao().insertProduct(it) }
    }
}