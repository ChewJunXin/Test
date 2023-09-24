package com.example.earthsustain.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.earthsustain.database.*
import com.example.earthsustain.database.EarthSustainRepository
import kotlinx.coroutines.launch

class EarthSustainViewModel(private val repository: EarthSustainRepository) : ViewModel() {

    // User
    fun insertUser(user: User) = viewModelScope.launch {
        repository.insertUser(user)
    }

    fun getUserByEmail(email: String) = viewModelScope.launch {
        repository.getUserByEmail(email)
    }

    fun updateUser(user: User) = viewModelScope.launch {
        repository.updateUser(user)
    }

    fun updatePassword(email: String, newPassword: String) = viewModelScope.launch {
        repository.updatePassword(email, newPassword)
    }

    fun deleteUserByEmail(email: String) = viewModelScope.launch {
        repository.deleteUserByEmail(email)
    }

    // Wallet
    fun insertWallet(wallet: Wallet) = viewModelScope.launch {
        repository.insertWallet(wallet)
    }

    fun getAllWallets() = viewModelScope.launch {
        repository.getAllWallets()
    }

    fun getWalletsByHolderEmail(holderEmail: String) = viewModelScope.launch {
        repository.getWalletsByHolderEmail(holderEmail)
    }

    fun getWalletIdByHolderEmail(holderEmail: String) = viewModelScope.launch {
        repository.getWalletIdByHolderEmail(holderEmail)
    }

    fun updateAmountByWalletId(walletId: Long, newAmount: Double) = viewModelScope.launch {
        repository.updateAmountByWalletId(walletId, newAmount)
    }

    fun updatePinCodeByWalletId(walletId: Long, newPinCode: Int) = viewModelScope.launch {
        repository.updatePinCodeByWalletId(walletId, newPinCode)
    }

    fun deleteWallet(walletId: Long) = viewModelScope.launch {
        repository.deleteWallet(walletId)
    }

    // Card
    fun insertCard(card: Card) = viewModelScope.launch {
        repository.insertCard(card)
    }

    fun getAllCards() = viewModelScope.launch {
        repository.getAllCards()
    }

    fun getCardsByHolderEmail(cardHolderEmail: String) = viewModelScope.launch {
        repository.getCardsByHolderEmail(cardHolderEmail)
    }

    fun deleteCard(cardId: Long) = viewModelScope.launch {
        repository.deleteCard(cardId)
    }

    fun deleteCardByHolderEmailAndNumber(cardHolderEmail: String, cardNumber: String) = viewModelScope.launch {
        repository.deleteCardByHolderEmailAndNumber(cardHolderEmail, cardNumber)
    }

    // Event
    fun insertEvent(event: Event) = viewModelScope.launch {
        repository.insertEvent(event)
    }

    fun updateEvent(event: Event) = viewModelScope.launch {
        repository.updateEvent(event)
    }

    fun deleteEvent(event: Event) = viewModelScope.launch {
        repository.deleteEvent(event)
    }

    fun getEventById(eventId: Long) = viewModelScope.launch {
        repository.getEventById(eventId)
    }

    fun getEventsByOrganiserEmail(organiserEmail: String) = viewModelScope.launch {
        repository.getEventsByOrganiserEmail(organiserEmail)
    }

    fun updateTotalJoin(eventId: Long, newTotalJoin: Int) = viewModelScope.launch {
        repository.updateTotalJoin(eventId, newTotalJoin)
    }

    // Donation
    fun insertDonation(donation: Donation) = viewModelScope.launch {
        repository.insertDonation(donation)
    }

    fun getDonationById(donationId: Long) = viewModelScope.launch {
        repository.getDonationById(donationId)
    }

    fun getDonationsByUserEmail(donateUserEmail: String) = viewModelScope.launch {
        repository.getDonationsByUserEmail(donateUserEmail)
    }

    fun getAllDonations() = viewModelScope.launch {
        repository.getAllDonations()
    }

    // Blog
    fun insertBlog(blog: Blog) = viewModelScope.launch {
        repository.insertBlog(blog)
    }

    fun updateBlog(blog: Blog) = viewModelScope.launch {
        repository.updateBlog(blog)
    }

    fun deleteBlog(blog: Blog) = viewModelScope.launch {
        repository.deleteBlog(blog)
    }

    fun getBlogById(blogId: Long) = viewModelScope.launch {
        repository.getBlogById(blogId)
    }

    fun getBlogsByAuthorEmail(authorEmail: String) = viewModelScope.launch {
        repository.getBlogsByAuthorEmail(authorEmail)
    }

    fun updateTotalComment(blogId: Long, newTotalComment: Int) = viewModelScope.launch {
        repository.updateTotalComment(blogId, newTotalComment)
    }

    // News
    fun insertNews(news: News) = viewModelScope.launch {
        repository.insertNews(news)
    }

    fun updateNews(news: News) = viewModelScope.launch {
        repository.updateNews(news)
    }

    fun deleteNews(news: News) = viewModelScope.launch {
        repository.deleteNews(news)
    }

    fun getNewsById(newsId: Long) = viewModelScope.launch {
        repository.getNewsById(newsId)
    }
}
