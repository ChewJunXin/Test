package com.example.earthsustain.database

data class Event(
    val eventId: String = "",
    val eventTitle: String = "",
    val eventDate: String = "",
    val eventDescription: String = "",
    val totalJoin: Int = 0,
    val joinerEmail: String = "",
    val eventStatus: String = "",
    val organiserEmail: String = ""
)
