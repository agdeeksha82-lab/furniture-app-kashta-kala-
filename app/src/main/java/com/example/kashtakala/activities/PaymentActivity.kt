package com.example.kashtakala.activities

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.kashtakala.R
import com.example.kashtakala.database.AppDatabase
import com.example.kashtakala.model.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val paymentGroup = findViewById<RadioGroup>(R.id.paymentGroup)
        val payBtn = findViewById<Button>(R.id.payBtn)

        val productName = intent.getStringExtra("productName") ?: "Unknown Product"
        val totalPrice = intent.getIntExtra("totalPrice", 0)
        val productPrice = intent.getIntExtra("productPrice", totalPrice)
        val quantity = intent.getIntExtra("quantity", 1)

        val database = AppDatabase.getDatabase(this)

        payBtn.setOnClickListener {
            val selectedId = paymentGroup.checkedRadioButtonId

            if (selectedId != -1) {
                val selectedButton = findViewById<RadioButton>(selectedId)
                val paymentMethod = selectedButton.text.toString()

                lifecycleScope.launch {
                    val orderDate = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(Date())
                    
                    val order = Order(
                        productName = productName,
                        productPrice = productPrice,
                        quantity = quantity,
                        totalPrice = totalPrice,
                        paymentMethod = paymentMethod,
                        orderDate = orderDate
                    )

                    withContext(Dispatchers.IO) {
                        database.orderDao().insertOrder(order)
                        database.cartDao().clearCart()
                    }

                    Toast.makeText(this@PaymentActivity, "Order Placed Successfully", Toast.LENGTH_LONG).show()
                    finish()
                }
            } else {
                Toast.makeText(this, "Please Select a Payment Method", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
