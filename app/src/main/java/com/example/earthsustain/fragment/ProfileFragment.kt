package com.example.earthsustain.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.earthsustain.R
import com.example.earthsustain.activity.EventActivity
import com.example.earthsustain.activity.LoginActivity
import com.example.earthsustain.database.UserViewModel

class ProfileFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val editButton = view.findViewById<Button>(R.id.editProfileButton)

        editButton.setOnClickListener {
            // Create an Intent to navigate to the password recovery screen (or any other activity)
            val intent = Intent(requireActivity(), EventActivity::class.java)
            intent.putExtra("openEditProfile", true)
            startActivity(intent)
        }

        val changePassword = view.findViewById<Button>(R.id.changePasswordButton)

        changePassword.setOnClickListener {
            // Create an Intent to navigate to the password recovery screen (or any other activity)
            val intent = Intent(requireActivity(), EventActivity::class.java)
            intent.putExtra("openChangePassword", true)
            startActivity(intent)
        }
    }
}