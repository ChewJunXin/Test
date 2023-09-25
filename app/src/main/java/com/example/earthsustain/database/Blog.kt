package com.example.earthsustain.database


data class Blog(
    val blogId: String = "",
    val blogTitle: String = "",
    val releaseDate: String = "",
    val authorEmail: String = "",
    val blogDetails: String = "",
    val comments: String = "",
    val commentHolderEmail: String = "",
    val totalComment: Int = 0
)