package com.example.earthsustain.fragment

import android.app.Activity
import android.content.Context
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
    private lateinit var profileImageView: ImageView
    private lateinit var browseImageButton: Button
    private lateinit var updateButton: Button
    private lateinit var storageRef: StorageReference
    private lateinit var databaseReference: DatabaseReference
    private lateinit var uri:Uri


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_admin_edit_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileImageView = view.findViewById(R.id.profileImageView)
        browseImageButton = view.findViewById(R.id.browseButton)
        updateButton = view.findViewById(R.id.updateButton)
        storageRef = FirebaseStorage.getInstance().reference
        databaseReference = FirebaseDatabase.getInstance().reference.child("Users").child(user.userId)

        // Set the visibility of other views to GONE
        view.findViewById<EditText>(R.id.firstNameEditText).visibility = View.GONE
        view.findViewById<EditText>(R.id.lastNameEditText).visibility = View.GONE
        view.findViewById<EditText>(R.id.phoneNumberEditText).visibility = View.GONE
        view.findViewById<EditText>(R.id.emailEditText).visibility = View.GONE

        Glide.with(requireContext()).load(user.imageLink).into(profileImageView)

        val galleryImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                profileImageView.setImageURI(it)
                if (it != null) {
                    uri = it
                }
            }
        )

        browseImageButton.setOnClickListener{
            galleryImage.launch("image/*")
        }

        updateButton.setOnClickListener {
            val imageRef =
                FirebaseStorage.getInstance().getReference("images").child(System.currentTimeMillis().toString())

            imageRef.putFile(uri).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Image upload is successful, get the download URL
                    imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                        val updatedImageUrl = downloadUri.toString()
                        updateUser(
                            requireContext(),
                            user,
                            user.firstName,
                            user.lastName,
                            user.phoneNumber,
                            user.email,
                            updatedImageUrl,
                            user.userId
                        )
                    }
                }
            } ?: run {
                Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUser(
        context: Context,
        user: User,
        updatedFirstName: String,
        updatedLastName: String,
        updatedPhoneNumber: String,
        updatedEmail: String,
        updatedImageUrl: String,
        userId: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Users").child(userId)

        val updatedUser = User(
            userId,
            updatedEmail,
            user.password,
            updatedFirstName,
            updatedLastName,
            updatedPhoneNumber,
            updatedImageUrl,
        )

        dbRef.setValue(updatedUser).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "User data updated successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to update user data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}