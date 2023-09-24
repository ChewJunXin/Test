package com.example.earthsustain


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.earthsustain.activity.DashActivity
import com.example.earthsustain.activity.EventActivity
import com.example.earthsustain.activity.LoginActivity
import com.example.earthsustain.database.EarthSustainDatabase

//import com.example.earthsustain.database.EarthSustainDatabase

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_programme)

//        val descriptionTextView = findViewById<TextView>(R.id.description)
//        descriptionTextView.movementMethod = ScrollingMovementMethod()

//        val database = EarthSustainDatabase.getInstance(this)
//        val userDao = database.userDao
//
//        userDao.getUserByEmail("dsafa")

//
//        val loginbtn = findViewById<TextView>(R.id.loginbtn)
//        val signupbtn = findViewById<TextView>(R.id.signupbtn)
//        loginbtn.setOnClickListener{
//            val intent = Intent(this, EventActivity::class.java)
//
//            startActivity(intent)
//        }
//
//        signupbtn.setOnClickListener{
//            ///
//        }
    }
}