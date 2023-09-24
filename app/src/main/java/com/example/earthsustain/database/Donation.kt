package com.example.earthsustain.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "donations",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["email"],
            childColumns = ["donateUserEmail"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Donation(
    @PrimaryKey(autoGenerate = true)
    val donationId: Long = 0,

    @ColumnInfo(name = "donateUserEmail")
    val donateUserEmail: String, // This will be used to reference the User entity to get the user name

    @ColumnInfo(name = "donationAmount")
    val donationAmount: Double,

    @ColumnInfo(name = "donateDate")
    val donateDate: String
)
