package com.example.earthsustain.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "blogs",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["email"],
            childColumns = ["authorEmail"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["email"],
            childColumns = ["commentHolderEmail"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Blog(
    @PrimaryKey(autoGenerate = true)
    val blogId: Long = 0,

    @ColumnInfo(name = "blogTitle")
    val blogTitle: String,

    @ColumnInfo(name = "releaseDate")
    val releaseDate: String,

    @ColumnInfo(name = "authorEmail")
    val authorEmail: String, // This will be used to reference the User entity to get the user name

    @ColumnInfo(name = "blogDetails")
    val blogDetails: String,

    @ColumnInfo(name = "comments")
    val comments: String, // Assuming comments are stored as a String

    @ColumnInfo(name = "commentHolderEmail")
    val commentHolderEmail: String, // This will be used to retrieve the name of the user

    @ColumnInfo(name = "totalComment")
    val totalComment: Int // Add the totalComment field
)