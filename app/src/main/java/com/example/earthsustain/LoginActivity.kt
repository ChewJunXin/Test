package com.example.earthsustain

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginbtn = findViewById<TextView>(R.id.loginbtn)

        loginbtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
//            intent.putExtra("username", "exampleUsername") // Pass any data you need
//            val username = intent.getStringExtra("username")
            startActivity(intent)
        }

    }


}