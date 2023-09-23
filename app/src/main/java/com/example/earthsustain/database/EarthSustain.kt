package com.example.earthsustain.database

//import androidx.room.Entity
//import androidx.room.ForeignKey
//import androidx.room.PrimaryKey

//
//@Entity
//data class User(
//    @PrimaryKey val email: String,
//    val password: String,
//    val firstName: String,
//    val lastName: String,
//    val phoneNumber: Int
//)
//
//@Entity(
//    tableName = "wallets",
//    foreignKeys = [
//        ForeignKey(
//            entity = User::class,
//            parentColumns = ["email"],
//            childColumns = ["holderEmail"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
//)
//data class Wallet(
//    @PrimaryKey(autoGenerate = true)
//    val walletId: Long = 0,
//    val amount: Double,
//    val pinCode: Int,
//    val holderEmail: String // This will be used to reference the User entity to get the user name
//)
//
//@Entity(
//    tableName = "cards",
//    foreignKeys = [
//        ForeignKey(
//            entity = User::class,
//            parentColumns = ["email"],
//            childColumns = ["cardHolderEmail"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
//)
//data class Card(
//    @PrimaryKey(autoGenerate = true)
//    val cardId: Long = 0,
//    val cardNumber: Int,
//    val cardType: String,
//    val cardHolderEmail: String, // Reference to the User entity to get the user name
//)
//
//@Entity(
//    tableName = "events",
//    foreignKeys = [
//        ForeignKey(
//            entity = User::class,
//            parentColumns = ["email"],
//            childColumns = ["organiserEmail"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
//)
//data class Event(
//    @PrimaryKey(autoGenerate = true)
//    val eventId: Long = 0,
//    val eventTitle: String,
//    val eventDate: String,
//    val eventDescription: String,
//    val totalJoin: Int,
//    val organiserEmail: String
//)
//
//
//@Entity(
//    tableName = "donations",
//    foreignKeys = [
//        ForeignKey(
//            entity = User::class,
//            parentColumns = ["email"],
//            childColumns = ["donateUserEmail"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
//)
//data class Donation(
//    @PrimaryKey(autoGenerate = true) val donationId: Long = 0,
//    val donateUserEmail: String, // This will be used to reference the User entity to get the user name
//    val donationAmount: Double,
//    val donateDate: String
//)
//
//
//@Entity(
//    tableName = "blogs",
//    foreignKeys = [
//        ForeignKey(
//            entity = User::class,
//            parentColumns = ["email"],
//            childColumns = ["authorEmail"],
//            onDelete = ForeignKey.CASCADE
//        ),
//        ForeignKey(
//            entity = User::class,
//            parentColumns = ["email"],
//            childColumns = ["commentHolderEmail"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
//)
//data class Blog(
//    @PrimaryKey(autoGenerate = true)
//    val blogId: Long = 0,
//    val blogTitle: String,
//    val releaseDate: String,
//    val authorEmail: String, // This will be used to reference the User entity to get the user name
//    val blogDetails: String,
//    val comments: String, // Assuming comments are stored as a String
//    val commentHolderEmail: String, // This will be used to retrieve the name of the user
//    val totalComment: Int // Add the totalComment field
//)
//
//data class News(
//    @PrimaryKey(autoGenerate = true)
//    val newsId: Long = 0,
//    val newsTitle: String,
//    val newsImage: String, // Assuming the image is stored as a String (file path or URL)
//    val newsDetails: String,
//    val newsDate: String,
//    val newsAuthor: String
//)
//
