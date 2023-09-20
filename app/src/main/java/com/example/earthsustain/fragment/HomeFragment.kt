package com.example.earthsustain.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.earthsustain.activity.EventActivity
import com.example.earthsustain.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using View Binding
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        // Set an OnClickListener for eventbtn
        binding.eventbtn.setOnClickListener {
            val intent = Intent(requireActivity(), EventActivity::class.java)
            startActivity(intent)
        }

        return view
    }

}