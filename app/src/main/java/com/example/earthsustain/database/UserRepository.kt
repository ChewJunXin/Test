package com.example.earthsustain.database

import com.google.firebase.database.DatabaseReference

class UserRepository(private val userDao: UserDao, private val databaseReference: DatabaseReference) {

    suspend fun insertUser(user: User) {
        val userId = userDao.insert(user)
        databaseReference.child(userId.toString()).setValue(user)
    }

    suspend fun updateUser(user: User) {
        userDao.update(user)
        databaseReference.child(user.id.toString()).setValue(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.delete(user)
        databaseReference.child(user.id.toString()).removeValue()
    }

    suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }

    suspend fun getAllUsers(): List<User> {
        return userDao.getAll()
    }
}