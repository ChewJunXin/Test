package com.example.earthsustain.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao{

    @Insert
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserByEmail(email: String): User

    @Query("UPDATE users SET password = :newPassword WHERE email = :email")
    fun updatePassword(email: String, newPassword: String)

    @Query("DELETE FROM users WHERE email = :email")
    fun deleteUserByEmail(email: String)
}