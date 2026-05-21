package com.example.kashtakala.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kashtakala.R
import com.example.kashtakala.model.Cart

class CartAdapter(
    private var cartList: List<Cart>,
    private val onDeleteClick: (Cart) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.cartProductImage)
        val productName: TextView = itemView.findViewById(R.id.cartProductName)
        val productPrice: TextView = itemView.findViewById(R.id.cartProductPrice)
        val deleteBtn: ImageButton = itemView.findViewById(R.id.deleteBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartList[position]
        holder.productName.text = item.name
        holder.productPrice.text = "₹${item.price}"
        holder.productImage.setImageResource(item.image)

        holder.deleteBtn.setOnClickListener {
            onDeleteClick(item)
        }
    }

    override fun getItemCount(): Int = cartList.size

    fun getCartList(): List<Cart> {
        return cartList
    }

    fun updateList(newList: List<Cart>) {
        cartList = newList
        notifyDataSetChanged()
    }
}
