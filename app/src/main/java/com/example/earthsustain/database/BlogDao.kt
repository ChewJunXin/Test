package com.example.earthsustain.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BlogDao {
    @Insert
    fun insert(blog: Blog)

    @Update
    fun update(blog: Blog)

    @Delete
    fun delete(blog: Blog)

    @Query("SELECT * FROM blogs WHERE blogId = :blogId")
    fun getBlogById(blogId: Long): Blog?

    @Query("SELECT * FROM blogs WHERE authorEmail = :authorEmail")
    fun getBlogsByAuthorEmail(authorEmail: String): List<Blog>

    // Method to update the totalComment for a blog based on blogId
    @Query("UPDATE blogs SET totalComment = :newTotalComment WHERE blogId = :blogId")
    fun updateTotalComment(blogId: Long, newTotalComment: Int)

    // Method to update blogTitle, releaseDate, and blogDetails based on blogId and authorEmail
    @Query("UPDATE blogs SET blogTitle = :newTitle, releaseDate = :newReleaseDate, blogDetails = :newDetails WHERE blogId = :blogId AND authorEmail = :authorEmail")
    fun updateBlogDetails(blogId: Long, authorEmail: String, newTitle: String, newReleaseDate: String, newDetails: String)

    // Method to show comments based on blogId
    @Query("SELECT comments FROM blogs WHERE blogId = :blogId")
    fun getCommentsByBlogId(blogId: Long): String?
}