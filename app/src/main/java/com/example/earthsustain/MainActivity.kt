package com.example.earthsustain


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.earthsustain.activity.DashActivity
import com.example.earthsustain.activity.EventActivity
import com.example.earthsustain.activity.LoginActivity
import com.example.earthsustain.database.EarthSustainDatabase
import com.example.earthsustain.database.EarthSustainRepository
import com.example.earthsustain.database.EarthSustainViewModel
import com.example.earthsustain.database.EarthSustainViewModelFactory
import com.example.earthsustain.database.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(){

//    private lateinit var database : EarthSustainDatabase
    private lateinit var viewModel: EarthSustainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val descriptionTextView = findViewById<TextView>(R.id.description)
//        descriptionTextView.movementMethod = ScrollingMovementMethod()

        // Initialize the repository
        val database = EarthSustainDatabase.getInstance(applicationContext)
        val repository = EarthSustainRepository(database)

        // Initialize the ViewModel
        val viewModelFactory = EarthSustainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EarthSustainViewModel::class.java)

        viewModel.insertUser(User("earthsustain2023@gmail.com", "earthsustain2023","Earth", "Sustain",  "01212345679"))



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