package com.example.earthsustain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Wallet::class, Card::class, Event::class, Donation::class, Blog::class, News::class], version = 1, exportSchema = false)
abstract class EarthSustainDatabase : RoomDatabase() {

    abstract val userDao: UserDao
    abstract val walletDao: WalletDao
    abstract val cardDao: CardDao
    abstract val eventDao: EventDao
    abstract val donationDao: DonationDao
    abstract val blogDao: BlogDao
    abstract val newsDao: NewsDao

    companion object {
        @Volatile
        private var INSTANCE: EarthSustainDatabase? = null

        fun getInstance(context: Context): EarthSustainDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EarthSustainDatabase::class.java,
                        "earthsustain_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
