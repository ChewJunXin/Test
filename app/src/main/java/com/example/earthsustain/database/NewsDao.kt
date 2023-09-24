package com.example.earthsustain.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NewsDao {

    @Insert
    fun insert(news: News)

    // Method to update news based on newsId
    @Update
    fun update(news: News)

    @Delete
    fun delete(news: News)

    @Query("SELECT * FROM news WHERE newsId = :newsId")
    fun getNewsById(newsId: Long): News

}