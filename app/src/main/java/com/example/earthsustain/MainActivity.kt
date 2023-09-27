package com.example.earthsustain


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
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

        val loginbtn = findViewById<TextView>(R.id.loginbtn)
        val signupbtn = findViewById<TextView>(R.id.signupbtn)

        database = FirebaseDatabase.getInstance().getReference("Users")
        val user = User("A001", "earthsustain2023@gmail.com", "earthsustain_2023", "Earth", "Sustain", "01133543533", "profile")
        val user1 = User("U001", "xuanjie.jong@gmail.com", "jong1234", "Xuan Jie", "Jong", "01232443678", "profile")
        val user2 = User("U002", "junxin.chew@gmail.com", "chew1234", "Jun Xin", "Chew", "01237645444", "profile")
        val user3 = User("U003", "zhihong.ho@gmail.com", "ho1234", "Zhi Hong", "Ho", "01265756768", "profile")
        val user4 = User("U004", "laifah.tan@gmail.com", "tan1234", "Lai Fah", "Tan", "01152432334", "profile")

        val usersMap = mapOf(
            "A001" to user,
            "U001" to user1,
            "U002" to user2,
            "U003" to user3,
            "U004" to user4
        )

        database.updateChildren(usersMap)


        //database = FirebaseDatabase.getInstance().getReference("Users")

        //val user = User("xuanjie.jong@gmail.com", "jong1234", "Xuan Jie", "Jong", "01232443678")
        //val child = user.email.replace(".", ",")

        loginbtn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)

            startActivity(intent)

        }

        signupbtn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("openSignup", true)
            startActivity(intent)
        }
//            database.child(child).setValue(user)
//                .addOnSuccessListener {
//                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
//            }
//                .addOnFailureListener {
//                Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
//            }

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