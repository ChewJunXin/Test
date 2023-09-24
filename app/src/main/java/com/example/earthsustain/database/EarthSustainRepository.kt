package com.example.earthsustain.database

import com.example.earthsustain.database.*

class EarthSustainRepository(private val database: EarthSustainDatabase) {

    // User
    suspend fun insertUser(user: User) {
        database.userDao.insertUser(user)
    }

    suspend fun getUserByEmail(email: String): User? {
        return database.userDao.getUserByEmail(email)
    }

    suspend fun updateUser(user: User) {
        database.userDao.updateUser(user)
    }

    suspend fun updatePassword(email: String, newPassword: String) {
        database.userDao.updatePassword(email, newPassword)
    }

    suspend fun deleteUserByEmail(email: String) {
        database.userDao.deleteUserByEmail(email)
    }

    // Wallet
    suspend fun insertWallet(wallet: Wallet) {
        database.walletDao.insertWallet(wallet)
    }

    suspend fun getAllWallets(): List<Wallet> {
        return database.walletDao.getAllWallets()
    }

    suspend fun getWalletsByHolderEmail(holderEmail: String): List<Wallet> {
        return database.walletDao.getWalletsByHolderEmail(holderEmail)
    }

    suspend fun getWalletIdByHolderEmail(holderEmail: String): Long? {
        return database.walletDao.getWalletIdByHolderEmail(holderEmail)
    }

    suspend fun updateAmountByWalletId(walletId: Long, newAmount: Double) {
        database.walletDao.updateAmountByWalletId(walletId, newAmount)
    }

    suspend fun updatePinCodeByWalletId(walletId: Long, newPinCode: Int) {
        database.walletDao.updatePinCodeByWalletId(walletId, newPinCode)
    }

    suspend fun deleteWallet(walletId: Long) {
        database.walletDao.deleteWallet(walletId)
    }

    // Card
    suspend fun insertCard(card: Card) {
        database.cardDao.insertCard(card)
    }

    suspend fun getAllCards(): List<Card> {
        return database.cardDao.getAllCards()
    }

    suspend fun getCardsByHolderEmail(cardHolderEmail: String): List<Card> {
        return database.cardDao.getCardsByHolderEmail(cardHolderEmail)
    }

    suspend fun deleteCard(cardId: Long) {
        database.cardDao.deleteCard(cardId)
    }

    suspend fun deleteCardByHolderEmailAndNumber(cardHolderEmail: String, cardNumber: String) {
        database.cardDao.deleteCardByHolderEmailAndNumber(cardHolderEmail, cardNumber)
    }

    // Event
    suspend fun insertEvent(event: Event) {
        database.eventDao.insert(event)
    }

    suspend fun updateEvent(event: Event) {
        database.eventDao.update(event)
    }

    suspend fun deleteEvent(event: Event) {
        database.eventDao.delete(event)
    }

    suspend fun getEventById(eventId: Long): Event? {
        return database.eventDao.getEventById(eventId)
    }

    suspend fun getEventsByOrganiserEmail(organiserEmail: String): List<Event> {
        return database.eventDao.getEventsByOrganiserEmail(organiserEmail)
    }

    suspend fun updateTotalJoin(eventId: Long, newTotalJoin: Int) {
        database.eventDao.updateTotalJoin(eventId, newTotalJoin)
    }

    // Add other methods for Event entity based on your needs

    // Donation
    suspend fun insertDonation(donation: Donation) {
        database.donationDao.insert(donation)
    }

    suspend fun getDonationById(donationId: Long): Donation? {
        return database.donationDao.getDonationById(donationId)
    }

    suspend fun getDonationsByUserEmail(donateUserEmail: String): List<Donation> {
        return database.donationDao.getDonationsByUserEmail(donateUserEmail)
    }

    suspend fun getAllDonations(): List<Donation> {
        return database.donationDao.getAllDonations()
    }

    // Blog
    suspend fun insertBlog(blog: Blog) {
        database.blogDao.insert(blog)
    }

    suspend fun updateBlog(blog: Blog) {
        database.blogDao.update(blog)
    }

    suspend fun deleteBlog(blog: Blog) {
        database.blogDao.delete(blog)
    }

    suspend fun getBlogById(blogId: Long): Blog? {
        return database.blogDao.getBlogById(blogId)
    }

    suspend fun getBlogsByAuthorEmail(authorEmail: String): List<Blog> {
        return database.blogDao.getBlogsByAuthorEmail(authorEmail)
    }

    suspend fun updateTotalComment(blogId: Long, newTotalComment: Int) {
        database.blogDao.updateTotalComment(blogId, newTotalComment)
    }

    // Add other methods for Blog entity based on your needs

    // News
    suspend fun insertNews(news: News) {
        database.newsDao.insert(news)
    }

    suspend fun updateNews(news: News) {
        database.newsDao.update(news)
    }

    suspend fun deleteNews(news: News) {
        database.newsDao.delete(news)
    }

    suspend fun getNewsById(newsId: Long): News? {
        return database.newsDao.getNewsById(newsId)
    }
}
