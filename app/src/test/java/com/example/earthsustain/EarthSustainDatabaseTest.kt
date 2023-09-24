package com.example.earthsustain.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EarthSustainDatabaseTest {

    private lateinit var db: EarthSustainDatabase
    private lateinit var userDao: UserDao
    private lateinit var walletDao: WalletDao
    // Add other DAOs as needed

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, EarthSustainDatabase::class.java).build()
        userDao = db.userDao
        walletDao = db.walletDao
        // Initialize other DAOs as needed
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testInsertAndRetrieveUser() = runBlocking {
        val user = User(email = "test@example.com", password = "password", firstName = "Test", lastName = "User", phoneNumber = 1234567890)
        userDao.insertUser(user)
        val retrievedUser = userDao.getUserByEmail("test@example.com")
        assertNotNull(retrievedUser)
        assertEquals(user, retrievedUser)
    }

    // Add more test cases for other DAOs and entities

}