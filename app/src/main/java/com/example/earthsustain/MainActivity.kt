package com.example.earthsustain


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.earthsustain.activity.EventActivity
import com.example.earthsustain.activity.LoginActivity
import com.example.earthsustain.database.User
import com.example.earthsustain.database.Wallet
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity(){


    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        database = FirebaseDatabase.getInstance().getReference("Users")
//
//        val user = User("earthsustain2023@gmail.com", "password", "EarthSustain", "", "01234567898")
//        database.child("earthsustain2023@gmail.com".replace(".", ",")).setValue(user)


//        database = FirebaseDatabase.getInstance().getReference("Wallets")
//
//        val wallet = Wallet(
//            amount = 100.0,
//            pinCode = 1234,
//            holderEmail = "earthsustain2023@gmail,com"
//        )
//
//        val walletId = database.push().key // This will generate a unique ID for the wallet
//        if (walletId != null) {
//            database.child(walletId).setValue(wallet)
//        }

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