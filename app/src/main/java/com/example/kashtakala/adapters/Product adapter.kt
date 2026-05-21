package com.example.kashtakala.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kashtakala.R
import com.example.kashtakala.model.Product

class ProductAdapter(
    private val productList: List<Product>,
    private val onCartClick: (Product) -> Unit,
    private val onWishlistClick: (Product) -> Unit,
    private val onProductClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.productImage)
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        val wishlistBtn: ImageButton = itemView.findViewById(R.id.wishlistBtn)
        val cartBtn: Button = itemView.findViewById(R.id.cartBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.productName.text = product.name
        holder.productPrice.text = "₹${product.price}"
        holder.productImage.setImageResource(product.image)

        // Individual button clicks
        holder.cartBtn.setOnClickListener { onCartClick(product) }
        holder.wishlistBtn.setOnClickListener { onWishlistClick(product) }
        
        // Full card click for details
        holder.itemView.setOnClickListener { onProductClick(product) }
    }

    override fun getItemCount(): Int = productList.size
}