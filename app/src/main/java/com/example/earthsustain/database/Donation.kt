package com.example.earthsustain.database

data class Donation(
    val donationId: String = "",
    val donateUserEmail: String = "",
    val donationAmount: Double = 0.0,
    val donateDate: String = ""
)