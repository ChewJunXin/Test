package com.example.earthsustain.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "wallets",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["email"],
            childColumns = ["holderEmail"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Wallet(
    @PrimaryKey(autoGenerate = true)
    val walletId: Long = 0,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "pinCode")
    val pinCode: Int,

    @ColumnInfo(name = "holderEmail")
    val holderEmail: String // This will be used to reference the User entity to get the user name
)

