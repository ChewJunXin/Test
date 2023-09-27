package com.example.earthsustain.fragment

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.earthsustain.R
import com.example.earthsustain.activity.EventActivity
import com.example.earthsustain.activity.LoginActivity
import com.example.earthsustain.database.User
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class EditProfileFragment : Fragment() {

    // Declare UI elements

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var editButton: Button
    private lateinit var id: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var imageUrl: String
    private lateinit var profileImageView: ImageView
    private lateinit var browseImageButton: Button
    private lateinit var uri: Uri

    private var storageRef = Firebase.storage
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize UI elements

        firstNameEditText = view.findViewById(R.id.firstNameEditText)
        lastNameEditText = view.findViewById(R.id.lastNameEditText)
        phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText)
        editButton = view.findViewById(R.id.EditButton)

        profileImageView = view.findViewById(R.id.profileImageView)
        browseImageButton = view.findViewById(R.id.browseImageButton)

        storageRef = FirebaseStorage.getInstance()

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

        editButton.setOnClickListener {
            if (validateForm()) {
                // If the form is valid, update the user's data
                updateUserData()
                val intent = Intent(requireActivity(), EventActivity::class.java)
                intent.putExtra("openProfile", true)
                startActivity(intent)
            }
        }

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val userEmail = currentUser.email // Get the email of the currently logged-in user

            // Reference to the custom user ID node in Firebase Realtime Database
            val usersRef = FirebaseDatabase.getInstance().getReference("Users")

            // Listen for changes and retrieve data based on the user's email
            usersRef.orderByChild("email").equalTo(userEmail)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (userSnapshot in dataSnapshot.children) {
                                val user = userSnapshot.getValue(User::class.java)
                                if (user != null) {

                                    id = user.userId
                                    email = user.email
                                    password = user.password
                                    imageUrl = user.imageLink
                                    firstNameEditText.setText(user.firstName)
                                    lastNameEditText.setText(user.lastName)
                                    phoneNumberEditText.setText(user.phoneNumber)

                                    // Load and display the profile picture using Glide
                                    Glide.with(requireContext())
                                        .load(user.imageLink) // Assuming "imageLink" contains the download URL of the image
                                        .into(profileImageView)
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

    private fun validateForm(): Boolean {


        val firstName = firstNameEditText.text.toString().trim()
        val lastName = lastNameEditText.text.toString().trim()
        val phoneNumber = phoneNumberEditText.text.toString().trim()


        if (firstName.isEmpty()) {
            firstNameEditText.error = "Enter your first name"
            firstNameEditText.requestFocus()
            return false
        }

        if (lastName.isEmpty()) {
            lastNameEditText.error = "Enter your last name"
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

        val dbRef = FirebaseDatabase.getInstance().getReference("Users").child(id)
        val newUser = User(
            email,
            password,
            firstName,
            lastName,
            phoneNumber,
            imageUrl,
            id
        )

        // Update the user's data in the database
        dbRef.setValue(newUser).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Data updated successfully
                Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
            } else {
                // Handle the case where the update fails
                Toast.makeText(requireContext(), "Failed to update profile", Toast.LENGTH_SHORT).show()
            }
        }
        if (uri != null) {
            // Upload the new image to Firebase Storage
            val imageRef =
                storageRef.getReference("images").child(System.currentTimeMillis().toString())

            imageRef.putFile(uri).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Image uploaded successfully
                    // Get the download URL of the uploaded image and update the imageUrl
                    imageRef.downloadUrl.addOnCompleteListener { downloadUrlTask ->
                        if (downloadUrlTask.isSuccessful) {
                            val newImageUrl = downloadUrlTask.result.toString()
                            // Update the imageUrl with the new URL in the database
                            dbRef.child("imageLink").setValue(newImageUrl)
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
