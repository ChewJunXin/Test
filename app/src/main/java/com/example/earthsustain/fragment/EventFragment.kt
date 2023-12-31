package com.example.earthsustain.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.earthsustain.R
import com.example.earthsustain.activity.EventActivity

class EventFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_event, container, false)

        // Declare variables
        val progbtn = view.findViewById<ImageView>(R.id.progbtn)
        val donabtn = view.findViewById<ImageView>(R.id.donabtn)

        val prog = view.findViewById<TextView>(R.id.programme)
        val dona = view.findViewById<TextView>(R.id.donation)

        // Set an OnClickListener for buttons
        // Either progbtn or prog is clicked
        val progClick = View.OnClickListener {
            val intent = Intent(requireContext(), EventActivity::class.java)
            intent.putExtra("programme", "approve")
            startActivity(intent)
        }

        progbtn.setOnClickListener(progClick)
        prog.setOnClickListener(progClick)

        // Either donabtn or dona is clicked
        val donaClick = View.OnClickListener {
            val intent = Intent(requireContext(), EventActivity::class.java)
            intent.putExtra("donation", "approve")
            startActivity(intent)
        }

        donabtn.setOnClickListener(donaClick)
        dona.setOnClickListener(donaClick)

        return view
    }
}