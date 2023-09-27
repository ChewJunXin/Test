package com.example.earthsustain.fragment

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.earthsustain.R
import com.example.earthsustain.activity.EventActivity
import com.example.earthsustain.activity.LoginActivity
import com.example.earthsustain.database.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    // Declare UI elements
    private lateinit var nameText: TextView
    private lateinit var phoneText: TextView
    private lateinit var emailText: TextView
    private lateinit var profilePicture: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)


    }override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameText = view.findViewById(R.id.nameText)
        emailText = view.findViewById(R.id.emailText)
        phoneText = view.findViewById(R.id.phoneText)
        profilePicture = view.findViewById(R.id.profilePicture)
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

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val userEmail = currentUser.email // Get the email of the currently logged-in user

            // Reference to the custom user ID node in Firebase Realtime Database
            val usersRef = FirebaseDatabase.getInstance().getReference("Users")

            // Listen for changes and retrieve data based on the user's email
            usersRef.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (userSnapshot in dataSnapshot.children) {
                            val user = userSnapshot.getValue(User::class.java)
                            if (user != null) {
                                val fullName = "${user.firstName} ${user.lastName}"
                                nameText.text = fullName
                                emailText.text = "Email: ${user.email}"
                                val formattedPhoneNumber = StringBuilder(user.phoneNumber).insert(3, "-").toString()
                                phoneText.text = "Phone Number: $formattedPhoneNumber"

                                // Load and display the profile picture using Glide
                                Glide.with(requireContext())
                                    .load(user.imageLink) // Assuming "imageLink" contains the download URL of the image
                                    .into(profilePicture)
                            }
                        }
                    } else {
                        // Handle the case where the user data for the logged-in user doesn't exist
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle error
                }
            })
        }
    }
}