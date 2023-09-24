package com.example.earthsustain


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.room.Room
import com.example.earthsustain.activity.DashActivity
import com.example.earthsustain.activity.EventActivity
import com.example.earthsustain.activity.LoginActivity
import com.example.earthsustain.database.EarthSustainDatabase
import com.example.earthsustain.database.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//import com.example.earthsustain.database.EarthSustainDatabase

class MainActivity : AppCompatActivity(){

    private lateinit var database : EarthSustainDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_programme)

//        val descriptionTextView = findViewById<TextView>(R.id.description)
//        descriptionTextView.movementMethod = ScrollingMovementMethod()

        database = Room.databaseBuilder(applicationContext, EarthSustainDatabase::class.java,
        "earthDB").build()

        GlobalScope.launch {
            database.userDao.insertUser(User("earthsustain2023@gmail.com", "earthsustain2023","Earth", "Sustain",  "01212345679"))
        }


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