package com.example.kashtakala.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kashtakala.R
import com.example.kashtakala.model.Wishlist

class WishlistAdapter(
    private val wishlistList: List<Wishlist>,
    private val onRemoveClick: (Wishlist) -> Unit
) : RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    class WishlistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.productImage)
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        val removeBtn: ImageButton = itemView.findViewById(R.id.wishlistBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return WishlistViewHolder(view)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val product = wishlistList[position]

        holder.productImage.setImageResource(product.productImage)
        holder.productName.text = product.productName
        holder.productPrice.text = "₹${product.productPrice}"

        // Set the icon to "active" state or a delete icon
        holder.removeBtn.setImageResource(android.R.drawable.btn_star_big_on)
        
        holder.removeBtn.setOnClickListener {
            onRemoveClick(product)
        }
    }

    override fun getItemCount(): Int = wishlistList.size
}
