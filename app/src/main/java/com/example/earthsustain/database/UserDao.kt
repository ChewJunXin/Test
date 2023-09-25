package com.example.earthsustain.database
import androidx.room.*

@Dao
interface UserDao {

        @Insert
        fun insert(user: User)

        @Update
        fun update(user: User)

        @Delete
        fun delete(user: User)

        @Query("SELECT * FROM user")
        fun getAll(): List<User>

        @Query("SELECT * FROM User WHERE id = :userId")
        fun getUserById(userId: Int): User?
    }

