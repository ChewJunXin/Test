package com.example.earthsustain.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class News(
    @PrimaryKey(autoGenerate = true)
    val newsId: Long = 0,

    @ColumnInfo(name = "newsTitle")
    val newsTitle: String,

    @ColumnInfo(name = "newsImage")
    val newsImage: String, // Assuming the image is stored as a String (file path or URL)

    @ColumnInfo(name = "newsDetails")
    val newsDetails: String,

    @ColumnInfo(name = "newsDate")
    val newsDate: String,

    @ColumnInfo(name = "newsAuthor")
    val newsAuthor: String
)