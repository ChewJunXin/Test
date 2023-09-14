package com.example.earthsustain


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        setContentView(R.layout.activity_test)
//
//        val loginbtn = findViewById<ImageView>(R.id.eventbtn)
//
//        loginbtn.setOnClickListener{
//            val intent = Intent(this, DashActivity::class.java)
//
//            startActivity(intent)
//        }

        val loginbtn = findViewById<TextView>(R.id.loginbtn)

        loginbtn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)

            startActivity(intent)
        }
    }

}