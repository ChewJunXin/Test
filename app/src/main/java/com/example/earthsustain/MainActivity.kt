package com.example.earthsustain


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.earthsustain.activity.LoginActivity

class MainActivity : AppCompatActivity(){

    private val enteredDigits = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginbtn = findViewById<TextView>(R.id.loginbtn)
        val signupbtn = findViewById<TextView>(R.id.signupbtn)
        loginbtn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)

            startActivity(intent)

        }

        signupbtn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("openSignup", true)
            startActivity(intent)
        }
    }
}