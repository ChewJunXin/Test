package com.example.earthsustain.fragment

import android.app.Activity
import android.content.Intent
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
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var profileImageView: ImageView
    private lateinit var browseImageButton: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageRef: StorageReference
    private lateinit var uri: Uri


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_admin_edit_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstNameEditText = view.findViewById(R.id.firstNameEditText)
        lastNameEditText = view.findViewById(R.id.lastNameEditText)
        phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText)
        updateButton = view.findViewById(R.id.updateButton)
        profileImageView = view.findViewById(R.id.profileImageView)
       //browseImageButton = view.findViewById(R.id.browseButton)

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
        storageRef = FirebaseStorage.getInstance().reference

        firstNameEditText.setText(user.firstName)
        lastNameEditText.setText(user.lastName)
        phoneNumberEditText.setText(user.phoneNumber)

        Glide.with(requireContext())
            .load(user.imageLink)
            .into(profileImageView)


        updateButton.setOnClickListener {
            if (validateForm()) {
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

    private fun updateProfileImage(imageUri: Uri) {
        val userRef = databaseReference.child("Users").child(user.userId)
        val imageRef = storageRef.child("images/${System.currentTimeMillis()}")

        imageRef.putFile(imageUri)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    imageRef.downloadUrl.addOnCompleteListener { downloadUrlTask ->
                        if (downloadUrlTask.isSuccessful) {
                            val newImageUrl = downloadUrlTask.result.toString()
                            userRef.child("imageLink").setValue(newImageUrl)
                            profileImageView.setImageURI(imageUri)
                        } else {
                            // Handle the case where getting the download URL fails
                        }
                    }
                } else {
                    // Handle the case where image upload fails
                }
            }
    }

    private fun updateUserData() {
        val firstName = firstNameEditText.text.toString().trim()
        val lastName = lastNameEditText.text.toString().trim()
        val phoneNumber = phoneNumberEditText.text.toString().trim()

        val userRef = databaseReference.child("Users").child(user.userId)

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
                    Toast.makeText(requireContext(), "User profile updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to update user profile", Toast.LENGTH_SHORT).show()
                }
            }
    }
}