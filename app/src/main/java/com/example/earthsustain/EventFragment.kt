package com.example.earthsustain

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class EventFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_event, container, false)

        // Find the eventbtn ImageView
        val eventBtn = view.findViewById<ImageView>(R.id.progbtn)

        // Set an OnClickListener for eventbtn
        eventBtn.setOnClickListener {
            val intent = Intent(requireContext(), EventActivity::class.java)
            intent.putExtra("event", "approve")
            startActivity(intent)
        }

        return view
    }
}