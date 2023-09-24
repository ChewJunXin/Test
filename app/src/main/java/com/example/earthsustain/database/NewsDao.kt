package com.example.earthsustain.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NewsDao {

    @Insert
    suspend fun insert(news: News)

    // Method to update news based on newsId
    @Update
    suspend fun update(news: News)

    @Delete
    suspend fun delete(news: News)

    @Query("SELECT * FROM news WHERE newsId = :newsId")
    suspend fun getNewsById(newsId: Long): News?

}