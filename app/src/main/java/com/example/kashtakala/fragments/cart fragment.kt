package com.example.kashtakala.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kashtakala.R
import com.example.kashtakala.activities.PaymentActivity
import com.example.kashtakala.adapters.CartAdapter
import com.example.kashtakala.database.AppDatabase
import com.example.kashtakala.model.Cart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartFragment : Fragment() {

    private lateinit var cartRv: RecyclerView
    private lateinit var totalPriceTv: TextView
    private lateinit var checkoutBtn: Button
    private lateinit var emptyCartTv: TextView
    private lateinit var database: AppDatabase
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        cartRv = view.findViewById<RecyclerView>(R.id.cartRv)
        totalPriceTv = view.findViewById<TextView>(R.id.totalPriceTv)
        checkoutBtn = view.findViewById<Button>(R.id.checkoutBtn)
        emptyCartTv = view.findViewById<TextView>(R.id.emptyCartTv)

        cartRv.layoutManager = LinearLayoutManager(context)
        database = AppDatabase.getDatabase(requireContext())

        loadCartItems()

        checkoutBtn.setOnClickListener {
            val adapter = cartRv.adapter as? CartAdapter
            val cartList = adapter?.getCartList()
            if (!cartList.isNullOrEmpty()) {
                val intent = Intent(requireContext(), PaymentActivity::class.java).apply {
                    putExtra("productName", if (cartList.size == 1) cartList[0].name else "Multiple Items")
                    putExtra("totalPrice", calculateTotal(cartList))
                }
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Cart is empty", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun loadCartItems() {
        viewLifecycleOwner.lifecycleScope.launch {
            val cartItems = database.cartDao().getCartItems()
            if (cartItems.isEmpty()) {
                emptyCartTv.visibility = View.VISIBLE
                cartRv.visibility = View.GONE
                totalPriceTv.text = "₹0"
            } else {
                emptyCartTv.visibility = View.GONE
                cartRv.visibility = View.VISIBLE
                cartAdapter = CartAdapter(cartItems) { item ->
                    deleteItem(item)
                }
                cartRv.adapter = cartAdapter
                totalPriceTv.text = "₹${calculateTotal(cartItems)}"
            }
        }
    }

    private fun deleteItem(item: Cart) {
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                database.cartDao().deleteCartItem(item.id)
            }
            loadCartItems()
        }
    }

    private fun calculateTotal(list: List<Cart>): Int {
        var total = 0
        for (item in list) {
            total += item.price
        }
        return total
    }
}
