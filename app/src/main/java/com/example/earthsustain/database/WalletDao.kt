package com.example.earthsustain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

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