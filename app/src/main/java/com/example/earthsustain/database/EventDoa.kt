package com.example.earthsustain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EventDao {
    @Insert
    suspend fun insert(event: Event)

    @Query("SELECT * FROM events")
    suspend fun getAllEvents(): List<Event>

    @Query("SELECT * FROM events WHERE eventId = :id")
    suspend fun getEventById(id: Long): Event?

    @Update
    suspend fun update(event: Event)

    @Query("DELETE FROM events WHERE eventId = :id")
    suspend fun deleteById(id: Long)
}
