package com.example.earthsustain.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.earthsustain.R
import com.example.earthsustain.activity.EventActivity
import com.example.earthsustain.activity.LoginActivity
import com.example.earthsustain.database.User
import com.example.earthsustain.database.UserViewModel

class LoginFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       //userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

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
            // Create an Intent to navigate to the password recovery screen (or any other activity)
            val intent = Intent(requireActivity(), EventActivity::class.java)
            intent.putExtra("openProfile", true)
            startActivity(intent)
            requireActivity().finish()
        }

    }


}