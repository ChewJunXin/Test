package com.example.earthsustain.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.earthsustain.R
import com.example.earthsustain.activity.AdminActivity
import com.example.earthsustain.activity.EventActivity
import com.example.earthsustain.activity.LoginActivity
import com.example.earthsustain.database.User
import com.example.earthsustain.databinding.FragmentSignupBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class LoginFragment : Fragment() {

    // Declare firebase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    // Declare UI elements
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordTextInputLayout: TextInputLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI elements
        emailEditText = view.findViewById(R.id.emailEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        passwordTextInputLayout = view.findViewById(R.id.passwordTextInputLayout)

        firebaseAuth = FirebaseAuth.getInstance()

        databaseReference = FirebaseDatabase.getInstance().reference

        val signUpButton = view.findViewById<Button>(R.id.signUpButton)

        signUpButton.setOnClickListener {
            // Create an Intent to navigate to the password recovery screen (or any other activity)
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.putExtra("openSignup", true)
            startActivity(intent)
        }

        val forgetPasswordButton = view.findViewById<TextView>(R.id.forgotPasswordText)

        forgetPasswordButton.setOnClickListener {
            // Create an Intent to navigate to the password recovery screen (or any other activity)
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.putExtra("openForgetPassword", true)
            startActivity(intent)
        }

        val loginButton = view.findViewById<TextView>(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString()

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = firebaseAuth.currentUser
                    if (currentUser != null) {
                        val userEmail = currentUser.email // Get the email of the currently logged-in user

                        // Reference to the custom user ID node in Firebase Realtime Database
                        val usersRef = databaseReference.child("Users")

                        // Listen for changes and retrieve data based on the user's email
                        usersRef.orderByChild("email").equalTo(userEmail)
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        for (userSnapshot in dataSnapshot.children) {
                                            val user = userSnapshot.getValue(User::class.java)
                                            if (user != null) {
                                                // Assuming you have a "userId" field in your User class
                                                val userId = user.userId

                                                if (userId == "A001") {
                                                    // Navigate to AdminActivity
                                                    val intent = Intent(
                                                        requireActivity(),
                                                        AdminActivity::class.java
                                                    )
                                                    intent.putExtra("openViewProfile", true)
                                                    startActivity(intent)
                                                    requireActivity().finish()
                                                } else {
                                                    // Now, update the password for this user based on the custom user ID
                                                    updatePasswordInRealtimeDatabase(
                                                        userId,
                                                        password
                                                    )

                                                    // Continue with your login logic
                                                    Toast.makeText(
                                                        requireContext(),
                                                        "Login Successful",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    val intent = Intent(
                                                        requireActivity(),
                                                        EventActivity::class.java
                                                    )
                                                    intent.putExtra("openProfile", true)
                                                    startActivity(intent)
                                                    requireActivity().finish()
                                                }
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
        }

    }

    private fun validateForm(): Boolean {

        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString()

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


        return true
    }

    private fun updatePasswordInRealtimeDatabase(userId: String, newPassword: String) {
        // Reference to the custom user ID node in Firebase Realtime Database
        val userRef = databaseReference.child("Users").child(userId)

        // Update the password field with the new password
        userRef.child("password").setValue(newPassword)
            .addOnCompleteListener { databaseUpdateResult ->
                if (databaseUpdateResult.isSuccessful) {
                    // Password updated in Realtime Database
                   // Toast.makeText(requireContext(), "Password updated in Realtime Database", Toast.LENGTH_SHORT).show()
                } else {
                    // Handle database update failure
                    Toast.makeText(requireContext(), "Failed to update password in Realtime Database", Toast.LENGTH_SHORT).show()
                    // You can also log the error for debugging purposes
                    val error = databaseUpdateResult.exception
                    if (error != null) {
                        Log.e("RealtimeDatabaseError", error.message.toString())
                    }
                }
            }
    }


}