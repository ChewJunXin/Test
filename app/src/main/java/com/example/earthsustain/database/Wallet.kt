package com.example.earthsustain.database

data class Wallet(
    val walletId: String = "",
    val amount: Double = 0.0,
    val pinCode: Int = 0,
    val holderEmail: String = ""
)
