package com.example.earthsustain.database
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM User WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Update
    suspend fun updateUser(user: User)

    @Query("UPDATE User SET password = :newPassword WHERE email = :email")
    suspend fun updatePassword(email: String, newPassword: String)

    @Query("DELETE FROM User WHERE email = :email")
    suspend fun deleteUserByEmail(email: String)
}


@Dao
interface WalletDao {
    @Insert
    suspend fun insertWallet(wallet: Wallet)

    @Query("SELECT * FROM wallets")
    suspend fun getAllWallets(): List<Wallet>

    @Query("SELECT * FROM wallets WHERE holderEmail = :holderEmail")
    suspend fun getWalletsByHolderEmail(holderEmail: String): List<Wallet>

    @Query("SELECT walletId FROM wallets WHERE holderEmail = :holderEmail LIMIT 1")
    suspend fun getWalletIdByHolderEmail(holderEmail: String): Long?

    // Method to update the amount using walletId
    @Query("UPDATE wallets SET amount = :newAmount WHERE walletId = :walletId")
    suspend fun updateAmountByWalletId(walletId: Long, newAmount: Double)

    // Method to update the pinCode using walletId
    @Query("UPDATE wallets SET pinCode = :newPinCode WHERE walletId = :walletId")
    suspend fun updatePinCodeByWalletId(walletId: Long, newPinCode: Int)

    @Query("DELETE FROM wallets WHERE walletId = :walletId")
    suspend fun deleteWallet(walletId: Long)

}

@Dao
interface CardDao {
    @Insert
    suspend fun insertCard(card: Card)

    @Query("SELECT * FROM cards")
    suspend fun getAllCards(): List<Card>

    @Query("SELECT * FROM cards WHERE cardHolderEmail = :cardHolderEmail")
    suspend fun getCardsByHolderEmail(cardHolderEmail: String): List<Card>

    @Query("DELETE FROM cards WHERE cardId = :cardId")
    suspend fun deleteCard(cardId: Long)

    // Method to delete a card record by cardHolderEmail and cardNumber
    @Query("DELETE FROM cards WHERE cardHolderEmail = :cardHolderEmail AND cardNumber = :cardNumber")
    suspend fun deleteCardByHolderEmailAndNumber(cardHolderEmail: String, cardNumber: String)

}

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

@Dao
interface BlogDao {
    @Insert
    suspend fun insert(blog: Blog)

    @Update
    suspend fun update(blog: Blog)

    @Delete
    suspend fun delete(blog: Blog)

    @Query("SELECT * FROM blogs WHERE blogId = :blogId")
    suspend fun getBlogById(blogId: Long): Blog?

    @Query("SELECT * FROM blogs WHERE authorEmail = :authorEmail")
    suspend fun getBlogsByAuthorEmail(authorEmail: String): List<Blog>

    // Method to update the totalComment for a blog based on blogId
    @Query("UPDATE blogs SET totalComment = :newTotalComment WHERE blogId = :blogId")
    suspend fun updateTotalComment(blogId: Long, newTotalComment: Int)

    // Method to update blogTitle, releaseDate, and blogDetails based on blogId and authorEmail
    @Query("UPDATE blogs SET blogTitle = :newTitle, releaseDate = :newReleaseDate, blogDetails = :newDetails WHERE blogId = :blogId AND authorEmail = :authorEmail")
    suspend fun updateBlogDetails(blogId: Long, authorEmail: String, newTitle: String, newReleaseDate: String, newDetails: String)

    // Method to show comments based on blogId
    @Query("SELECT comments FROM blogs WHERE blogId = :blogId")
    suspend fun getCommentsByBlogId(blogId: Long): String?
}

@Dao
interface NewsDao {

    @Insert
    suspend fun insert(news: News)

    // Method to update news based on newsId
    @Update
    suspend fun update(news: News)

    @Delete
    suspend fun delete(news: News)

    @Query("SELECT * FROM news WHERE newsId = :newsId")
    suspend fun getNewsById(newsId: Long): News?

}