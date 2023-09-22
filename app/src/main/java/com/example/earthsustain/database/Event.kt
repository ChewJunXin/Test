package com.example.earthsustain.database

//import androidx.room.Entity
//import androidx.room.PrimaryKey

//@Entity(tableName = "events")
data class Event(
//    @PrimaryKey(autoGenerate = true)
    val eventId: Long = 0,
    val eventTitle: String,
    val eventDate: String,
    val eventDescription: String,
    val totalJoin: Int,
    val organiserEmail: String
)