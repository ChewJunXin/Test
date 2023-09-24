package com.example.earthsustain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WalletDao {
    @Insert
    fun insertWallet(wallet: Wallet)

    @Query("SELECT * FROM wallets")
    fun getAllWallets(): List<Wallet>

    @Query("SELECT * FROM wallets WHERE holderEmail = :holderEmail")
    fun getWalletsByHolderEmail(holderEmail: String): List<Wallet>

    @Query("SELECT walletId FROM wallets WHERE holderEmail = :holderEmail LIMIT 1")
    fun getWalletIdByHolderEmail(holderEmail: String): Long?

    // Method to update the amount using walletId
    @Query("UPDATE wallets SET amount = :newAmount WHERE walletId = :walletId")
    fun updateAmountByWalletId(walletId: Long, newAmount: Double)

    // Method to update the pinCode using walletId
    @Query("UPDATE wallets SET pinCode = :newPinCode WHERE walletId = :walletId")
    fun updatePinCodeByWalletId(walletId: Long, newPinCode: Int)

    @Query("DELETE FROM wallets WHERE walletId = :walletId")
    fun deleteWallet(walletId: Long)

}