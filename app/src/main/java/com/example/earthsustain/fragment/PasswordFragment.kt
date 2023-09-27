package com.example.earthsustain.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.earthsustain.R
import com.example.earthsustain.activity.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class PasswordFragment : Fragment() {

    private lateinit var emailText: EditText
    private lateinit var resetButton: Button
    private lateinit var instructionText: TextView

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resetButton = view.findViewById(R.id.recoverButton)
        emailText = view.findViewById(R.id.emailEditText)
        instructionText = view.findViewById(R.id.instructionText)

        auth = FirebaseAuth.getInstance()

        // Check if the user is already logged in
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is logged in, set the email to the current user's email
            emailText.setText(currentUser.email)
            emailText.isEnabled = false // Disable editing of email field
            emailText.isEnabled = false
        }

        resetButton.setOnClickListener {
            val email = emailText.text.toString()
            auth.sendPasswordResetEmail(email).addOnSuccessListener {
                Toast.makeText(requireContext(), "Please Check your email", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener{
                    Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                }
        }
    }
}