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
import android.widget.TextView
import android.widget.Toast
import com.example.earthsustain.R
import com.example.earthsustain.activity.EventActivity
import com.example.earthsustain.activity.LoginActivity
import com.example.earthsustain.databinding.FragmentSignupBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth



class LoginFragment : Fragment() {

    // Declare firebase
    private lateinit var firebaseAuth: FirebaseAuth
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
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(){
                if (it.isSuccessful){
                    // Create an Intent to navigate to the password recovery screen (or any other activity)
                    Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireActivity(), EventActivity::class.java)
                    intent.putExtra("openProfile", true)
                    startActivity(intent)
                    requireActivity().finish()

                } else{
                    Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
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


}