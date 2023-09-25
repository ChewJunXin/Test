package com.example.earthsustain.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.earthsustain.R
import com.example.earthsustain.activity.LoginActivity
import com.example.earthsustain.database.User
import com.example.earthsustain.database.UserViewModel
import com.google.android.material.textfield.TextInputLayout


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

    lateinit var userViewModel: UserViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

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

        val firstName = firstNameEditText.text.toString()
        val lastName = lastNameEditText.text.toString()
        val phone = phoneNumberEditText.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        // Set an OnClickListener for the Sign Up button
        signUpButton.setOnClickListener {
            // Perform validation here
            if (validateForm()) {
                // Registration is valid, you can proceed with registration
                val user = User(0, firstName, lastName, phone, email, password) // Create a User object with user data
                userViewModel.insertUser(user)
                Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(intent)
            }
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
}