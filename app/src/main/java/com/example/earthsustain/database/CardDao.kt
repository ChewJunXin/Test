package com.example.earthsustain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CardDao {
    @Insert
    fun insertCard(card: Card)

    @Query("SELECT * FROM cards")
    fun getAllCards(): List<Card>

    @Query("SELECT * FROM cards WHERE cardHolderEmail = :cardHolderEmail")
    fun getCardsByHolderEmail(cardHolderEmail: String): List<Card>

    @Query("DELETE FROM cards WHERE cardId = :cardId")
    fun deleteCard(cardId: Long)

    // Method to delete a card record by cardHolderEmail and cardNumber
    @Query("DELETE FROM cards WHERE cardHolderEmail = :cardHolderEmail AND cardNumber = :cardNumber")
    fun deleteCardByHolderEmailAndNumber(cardHolderEmail: String, cardNumber: String)

}
