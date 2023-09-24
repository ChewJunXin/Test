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
    private lateinit var cardDao: CardDao
    private lateinit var eventDao: EventDao
    private lateinit var donationDao: DonationDao
    private lateinit var blogDao: BlogDao
    private lateinit var newsDao: NewsDao


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, EarthSustainDatabase::class.java).build()
        userDao = db.userDao
        walletDao = db.walletDao
        cardDao = db.cardDao
        eventDao = db.eventDao
        donationDao = db.donationDao
        blogDao = db.blogDao
        newsDao = db.newsDao

    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    suspend fun testInsertAndRetrieveUser()  {
        val user = User("earthsustain2023@gmail.com","earthsustain2023","Earth", "Sustain",  1212345679)
        userDao.insertUser(user)
        val retrievedUser = userDao.getUserByEmail("earthsustain2023@gmail.com")
        assertNotNull(retrievedUser)
        assertEquals(user, retrievedUser)
    }


}