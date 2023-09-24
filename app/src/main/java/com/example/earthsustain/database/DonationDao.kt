package com.example.earthsustain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DonationDao {
    @Insert
    suspend fun insert(donation: Donation)

    @Query("SELECT * FROM donations WHERE donationId = :donationId")
    suspend fun getDonationById(donationId: Long): Donation?

    @Query("SELECT * FROM donations WHERE donateUserEmail = :donateUserEmail")
    suspend fun getDonationsByUserEmail(donateUserEmail: String): List<Donation>

    // Method to retrieve all donation records
    @Query("SELECT * FROM donations")
    suspend fun getAllDonations(): List<Donation>
}
