package com.example.earthsustain.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "cards",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["email"],
            childColumns = ["cardHolderEmail"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Card(
    @PrimaryKey(autoGenerate = true)
    val cardId: Long = 0,

    @ColumnInfo(name = "cardNumber")
    val cardNumber: Int,

    @ColumnInfo(name = "cardType")
    val cardType: String,

    @ColumnInfo(name = "cardHolderEmail")
    val cardHolderEmail: String // Reference to the User entity to get the user name
)
