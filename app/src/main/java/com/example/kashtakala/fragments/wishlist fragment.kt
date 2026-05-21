package com.example.kashtakala.fragments



import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class WishlistFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val layout = LinearLayout(requireContext())

        layout.orientation = LinearLayout.VERTICAL

        layout.gravity = Gravity.CENTER

        layout.setBackgroundColor(
            android.graphics.Color.parseColor("#EFEBE9")
        )

        val title = TextView(requireContext())

        title.text = "Wishlist Screen"

        title.textSize = 28f

        title.setTextColor(
            android.graphics.Color.parseColor("#3E2723")
        )

        layout.addView(title)

        return layout
    }
}