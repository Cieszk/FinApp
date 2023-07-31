package pl.team.finap.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import pl.team.finap.database.entities.Wallet
import pl.team.finap.database.entities.embedded.WalletWithTransactions


@Dao
interface WalletDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallet(wallet: Wallet): Long

    @Update
    suspend fun updateWallet(wallet: Wallet)

    @Delete
    suspend fun deleteWallet(wallet: Wallet)

    @Query("SELECT * FROM WALLETS")
    suspend fun getAllWallets(): List<Wallet>

    @Query("SELECT * FROM WALLETS WHERE wallet_id = :id")
    suspend fun getWalletById(id: Long): Wallet

    @Transaction
    @Query("SELECT * FROM WALLETS WHERE wallet_id = :id")
    suspend fun getWalletWithTransactions(id: Long): WalletWithTransactions
}