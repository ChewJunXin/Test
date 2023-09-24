package com.example.earthsustain.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EventDao {
    @Insert
    suspend fun insert(event: Event)

    @Update
    suspend fun update(event: Event)

    @Delete
    suspend fun delete(event: Event)

    @Query("SELECT * FROM events WHERE eventId = :eventId")
    suspend fun getEventById(eventId: Long): Event?

    @Query("SELECT * FROM events WHERE organiserEmail = :organiserEmail")
    suspend fun getEventsByOrganiserEmail(organiserEmail: String): List<Event>

    // Method to update the totalJoin for an event based on eventId
    @Query("UPDATE events SET totalJoin = :newTotalJoin WHERE eventId = :eventId")
    suspend fun updateTotalJoin(eventId: Long, newTotalJoin: Int)

    // Method to update an event based on organiserEmail and eventId
    @Query("UPDATE events SET eventTitle = :newEventTitle, eventDate = :newEventDate, eventDescription = :newEventDescription WHERE organiserEmail = :organiserEmail AND eventId = :eventId")
    suspend fun updateEvent(organiserEmail: String, eventId: Long, newEventTitle: String, newEventDate: String, newEventDescription: String)

    // Method to delete an event based on organiserEmail and eventId
    @Query("DELETE FROM events WHERE organiserEmail = :organiserEmail AND eventId = :eventId")
    suspend fun deleteEvent(organiserEmail: String, eventId: Long)

    // Method to retrieve all events based on joinerEmail
    @Query("SELECT * FROM events WHERE joinerEmail = :joinerEmail")
    suspend fun getEventsByJoinerEmail(joinerEmail: String): List<Event>

    // Method to retrieve events based on joinerEmail and eventStatus
    @Query("SELECT * FROM events WHERE joinerEmail = :joinerEmail AND eventStatus = :eventStatus")
    suspend fun getEventsByJoinerEmailAndStatus(joinerEmail: String, eventStatus: String): List<Event>

    // Method to set eventStatus based on eventId
    @Query("UPDATE events SET eventStatus = :eventStatus WHERE eventId = :eventId")
    suspend fun setEventStatus(eventId: Long, eventStatus: String)
}
