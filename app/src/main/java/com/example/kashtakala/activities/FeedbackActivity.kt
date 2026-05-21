package com.example.kashtakala.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.kashtakala.R
import com.example.kashtakala.database.AppDatabase
import com.example.kashtakala.model.Feedback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedbackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val reviewEt = findViewById<EditText>(R.id.reviewEt)
        val submitBtn = findViewById<Button>(R.id.submitBtn)
        val productName = intent.getStringExtra("productName") ?: "Modern Sofa"
        val database = AppDatabase.getDatabase(this)

        submitBtn.setOnClickListener {
            val rating = ratingBar.rating
            val review = reviewEt.text.toString().trim()

            if (review.isNotEmpty()) {
                lifecycleScope.launch {
                    val feedback = Feedback(productName = productName, rating = rating, review = review)
                    withContext(Dispatchers.IO) {
                        database.feedbackDao().insertFeedback(feedback)
                    }
                    Toast.makeText(this@FeedbackActivity, "Feedback Submitted", Toast.LENGTH_LONG).show()
                    reviewEt.text.clear()
                    ratingBar.rating = 0f
                }
            } else {
                Toast.makeText(this, "Please Enter Feedback", Toast.LENGTH_SHORT).show()
            }
        }
    }
}