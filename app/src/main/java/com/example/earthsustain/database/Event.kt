package com.example.earthsustain.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "events",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["email"],
            childColumns = ["organiserEmail"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Event(
    @PrimaryKey(autoGenerate = true)
    val eventId: Long = 0,

    @ColumnInfo(name = "eventTitle")
    val eventTitle: String,

    @ColumnInfo(name = "eventDate")
    val eventDate: String,

    @ColumnInfo(name = "eventDescription")
    val eventDescription: String,

    @ColumnInfo(name = "totalJoin")
    val totalJoin: Int,

    @ColumnInfo(name = "joinerEmail")
    val joinerEmail: String,

    @ColumnInfo(name = "eventStatus")
    val eventStatus: String,

    @ColumnInfo(name = "organiserEmail")
    val organiserEmail: String
)