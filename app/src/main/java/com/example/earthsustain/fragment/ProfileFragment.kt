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
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.earthsustain.R
import com.example.earthsustain.activity.EventActivity
import com.example.earthsustain.activity.LoginActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    // Declare UI elements
    private lateinit var nameText: TextView
    private lateinit var phoneText: TextView
    private lateinit var emailText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameText = view.findViewById(R.id.nameText)
        emailText = view.findViewById(R.id.emailText)
        phoneText = view.findViewById(R.id.phoneText)
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

        val uid = Firebase.auth.currentUser?.email
        val emailKey = uid!!.replace(".", ",")
        val uidRef = Firebase.database.reference.child("Users").child(emailKey)
        uidRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val result = it.result
                val firstName = result.child("firstName").getValue(String::class.java)
                val lastName = result.child("lastName").getValue(String::class.java)
                val fullName = "$firstName $lastName"
                val email = result.child("email").getValue(String::class.java)
                val phoneNumber = result.child("phoneNumber").getValue(String::class.java)

                nameText.text = "$fullName"
                emailText.text = "Email: $email"
                phoneText.text = "Phone Number: $phoneNumber"


            } else {
                Log.d(TAG, "${it.exception?.message}") //Never ignore potential errors!
            }
        }
    }
}