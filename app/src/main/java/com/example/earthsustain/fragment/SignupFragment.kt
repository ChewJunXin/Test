package com.example.earthsustain.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.earthsustain.R
import com.example.earthsustain.activity.LoginActivity
import com.example.earthsustain.database.User
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.UploadTask


class SignupFragment : Fragment() {

    // Declare UI elements
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var passwordTextInputLayout: TextInputLayout
    private lateinit var confirmPasswordTextInputLayout: TextInputLayout
    private lateinit var profileImageView: ImageView
    private lateinit var browseImageButton: Button
    private var uri: Uri? = null // Initialize uri as nullable



    // Declare firebase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dbReference: DatabaseReference
    private var storageRef = Firebase.storage


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        // Initialize UI elements
        emailEditText = view.findViewById(R.id.emailEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordEditText)
        firstNameEditText = view.findViewById(R.id.firstNameEditText)
        lastNameEditText = view.findViewById(R.id.lastNameEditText)
        phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText)
        signUpButton = view.findViewById(R.id.signUpButton)
        passwordTextInputLayout = view.findViewById(R.id.passwordTextInputLayout)
        confirmPasswordTextInputLayout = view.findViewById(R.id.confirmPasswordTextInputLayout)
        profileImageView =  view.findViewById(R.id.profileImageView)
        browseImageButton =  view.findViewById(R.id.browseImageButton)


        firebaseAuth = FirebaseAuth.getInstance()
        dbReference = FirebaseDatabase.getInstance().getReference("Users")
        storageRef = FirebaseStorage.getInstance()

        // Set an OnClickListener for the Sign Up button
        signUpButton.setOnClickListener {
            // Perform validation here
            if (validateForm()) {
                saveUserData()
            }
        }

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

        return view
    }

    private fun validateForm(): Boolean {

        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()
        val firstName = firstNameEditText.text.toString().trim()
        val lastName = lastNameEditText.text.toString().trim()
        val phoneNumber = phoneNumberEditText.text.toString().trim()

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Enter a valid email address"
            emailEditText.requestFocus()
            return false
        }

        if (password.isEmpty() || password.length < 6) {
            passwordTextInputLayout.error = "Password must be at least 6 characters"
            passwordEditText.requestFocus()
            return false
        }

        if (confirmPassword.isEmpty() || confirmPassword != password) {
            confirmPasswordTextInputLayout.error = "Passwords do not match"
            confirmPasswordEditText.requestFocus()
            return false
        }

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

    private fun saveUserData() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString()
        val firstName = firstNameEditText.text.toString().trim()
        val lastName = lastNameEditText.text.toString().trim()
        val phoneNumber = phoneNumberEditText.text.toString().trim()

        // Get a reference to the "Users" node in Firebase Realtime Database
        val usersDatabase = FirebaseDatabase.getInstance().getReference("Users")

        // Read all child nodes under "Users" to find the next available ID
        usersDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usedIds = mutableSetOf<String>()

                // Iterate through all child nodes and add their keys to the set
                for (childSnapshot in dataSnapshot.children) {
                    val key = childSnapshot.key
                    if (key != null) {
                        usedIds.add(key)
                    }
                }

                // Find the smallest missing ID
                var newIdNumber = 1
                while (usedIds.contains("U00$newIdNumber")) {
                    newIdNumber++
                }

                // Format the new ID
                val Id = "U00$newIdNumber"

                val imageRef = if (uri != null) {
                    // Use the user-uploaded image if available
                    storageRef.getReference("images").child(System.currentTimeMillis().toString())
                } else {
                    // Use a default image if the user didn't upload an image
                    val defaultImageUrl = "https://firebasestorage.googleapis.com/v0/b/earthsustaindatabase-d0ea8.appspot.com/o/images%2F1695850269225?alt=media&token=e33f6c6f-a5cc-40fa-8515-45f7946ac909"
                    Uri.parse(defaultImageUrl).let { Firebase.storage.getReferenceFromUrl(it.toString()) }
                }

                // Check if the user uploaded an image
                if (uri != null) {
                    imageRef.putFile(uri!!).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Image upload is successful, get the download URL
                            imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                                // Now you can use downloadUri.toString() to get the download URL
                                val imageUrl = downloadUri.toString()

                                val user = User(
                                    Id,
                                    email,
                                    password,
                                    firstName,
                                    lastName,
                                    phoneNumber,
                                    imageUrl
                                )

                                // Continue with user registration using the uploaded image URL
                                firebaseAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener() {
                                        if (it.isSuccessful) {
                                            // Save user data with the generated ID
                                            usersDatabase.child(Id).setValue(user)
                                                .addOnCompleteListener() {
                                                    Toast.makeText(
                                                        requireContext(),
                                                        "Registration Successful",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    val intent = Intent(
                                                        requireActivity(),
                                                        LoginActivity::class.java
                                                    )
                                                    startActivity(intent)
                                                }.addOnFailureListener {
                                                    Toast.makeText(
                                                        requireContext(),
                                                        "Registration Failed",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                        }
                                    }
                            }
                        } else {
                            // Handle the case where image upload fails
                            // ...
                        }
                    }
                } else {
                    // User didn't upload an image, use the default image URL
                    val defaultImageUrl = "https://firebasestorage.googleapis.com/v0/b/earthsustaindatabase-d0ea8.appspot.com/o/images%2F1695850269225?alt=media&token=e33f6c6f-a5cc-40fa-8515-45f7946ac909"
                    val user = User(
                        Id,
                        email,
                        password,
                        firstName,
                        lastName,
                        phoneNumber,
                        defaultImageUrl
                    )

                    // Continue with user registration using the default image URL
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener() {
                            if (it.isSuccessful) {
                                // Save user data with the generated ID
                                usersDatabase.child(Id).setValue(user)
                                    .addOnCompleteListener() {
                                        Toast.makeText(
                                            requireContext(),
                                            "Registration Successful",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        val intent = Intent(
                                            requireActivity(),
                                            LoginActivity::class.java
                                        )
                                        startActivity(intent)
                                    }.addOnFailureListener {
                                        Toast.makeText(
                                            requireContext(),
                                            "Registration Failed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }
                        }
                }
            }


            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
                Toast.makeText(
                    requireContext(),
                    "Failed to read data: ${databaseError.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}