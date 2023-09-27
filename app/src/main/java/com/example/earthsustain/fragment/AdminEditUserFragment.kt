package com.example.earthsustain.fragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.earthsustain.R
import com.example.earthsustain.database.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AdminEditUserFragment (private val user: User) : Fragment() {
    // Declare UI elements
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var profileImageView: ImageView
    private lateinit var browseImageButton: Button

    // Declare Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageRef: StorageReference
    private lateinit var uri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_edit_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI elements
        firstNameEditText = view.findViewById(R.id.firstNameEditText)
        lastNameEditText = view.findViewById(R.id.lastNameEditText)
        phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText)
        updateButton = view.findViewById(R.id.updateButton)
        profileImageView = view.findViewById(R.id.profileImageView)
        browseImageButton = view.findViewById(R.id.browseButton)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
        storageRef = FirebaseStorage.getInstance().reference

        // Initialize the ActivityResultLauncher for profile picture selection
        val galleryImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                profileImageView.setImageURI(it)
                if (it != null) {
                    uri = it
                }
            }
        )

        browseImageButton.setOnClickListener {

            galleryImage.launch("image/*")
        }

        // Set the user's current information
        firstNameEditText.setText(user.firstName)
        lastNameEditText.setText(user.lastName)
        phoneNumberEditText.setText(user.phoneNumber)

        // Load and display the user's current profile picture using Glide
        Glide.with(requireContext())
            .load(user.imageLink)
            .into(profileImageView)

        updateButton.setOnClickListener {
            if (validateForm()) {
                // If the form is valid, update the user's data
                updateUserData()
            }
        }
    }

    private fun validateForm(): Boolean {
        val firstName = firstNameEditText.text.toString().trim()
        val lastName = lastNameEditText.text.toString().trim()
        val phoneNumber = phoneNumberEditText.text.toString().trim()

        if (firstName.isEmpty()) {
            firstNameEditText.error = "Enter the first name"
            firstNameEditText.requestFocus()
            return false
        }

        if (lastName.isEmpty()) {
            lastNameEditText.error = "Enter the last name"
            lastNameEditText.requestFocus()
            return false
        }

        if (phoneNumber.isEmpty() || !phoneNumber.matches(Regex("\\d{10,12}"))) {
            phoneNumberEditText.error = "Enter a valid phone number (10-12 digits)"
            phoneNumberEditText.requestFocus()
            return false
        }

        return true
    }

    private fun updateUserData() {
        val firstName = firstNameEditText.text.toString().trim()
        val lastName = lastNameEditText.text.toString().trim()
        val phoneNumber = phoneNumberEditText.text.toString().trim()

        // Reference to the user's data in Firebase Realtime Database
        val userRef = databaseReference.child("Users").child(user.userId)

        // Update the user's data in the database
        val updatedUser = User(
            user.email,
            user.password,
            firstName,
            lastName,
            phoneNumber,
            user.imageLink,
            user.userId
        )

        userRef.setValue(updatedUser)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Data updated successfully
                    Toast.makeText(requireContext(), "User profile updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    // Handle the case where the update fails
                    Toast.makeText(requireContext(), "Failed to update user profile", Toast.LENGTH_SHORT).show()
                }
            }

        if (uri != null) {
            // Upload the new image to Firebase Storage
            val imageRef = storageRef.child("images/${System.currentTimeMillis()}")
            imageRef.putFile(uri)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Image uploaded successfully
                        // Get the download URL of the uploaded image and update the imageUrl
                        imageRef.downloadUrl.addOnCompleteListener { downloadUrlTask ->
                            if (downloadUrlTask.isSuccessful) {
                                val newImageUrl = downloadUrlTask.result.toString()
                                // Update the imageUrl with the new URL in the database
                                userRef.child("imageLink").setValue(newImageUrl)
                            } else {
                                // Handle the case where getting the download URL fails
                            }
                        }
                    } else {
                        // Handle the case where image upload fails
                    }
                }
        }
    }
}