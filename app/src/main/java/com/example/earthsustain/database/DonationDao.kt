package com.example.earthsustain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DonationDao {
    @Insert
    fun insert(donation: Donation)

    @Query("SELECT * FROM donations WHERE donationId = :donationId")
    fun getDonationById(donationId: Long): Donation?

    @Query("SELECT * FROM donations WHERE donateUserEmail = :donateUserEmail")
    fun getDonationsByUserEmail(donateUserEmail: String): List<Donation>

    // Method to retrieve all donation records
    @Query("SELECT * FROM donations")
    fun getAllDonations(): List<Donation>
}
