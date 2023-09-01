package pl.team.finap.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.team.finap.database.entities.TransactionType
import pl.team.finap.database.entities.Wallet

@Dao
interface WalletDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWallet(wallet: Wallet): Long

    @Update
    fun updateWallet(wallet: Wallet)

    @Delete
    fun deleteWallet(wallet: Wallet)

    @Query("SELECT * FROM WALLETS")
    fun getAllWallets(): Flow<List<Wallet>>

    @Query("SELECT * FROM WALLETS WHERE wallet_id = :id")
    fun getWalletById(id: Long): Wallet

    @Query("SELECT * FROM WALLETS LIMIT 1")
    fun getOnlyWallet(): Wallet?

    @Query("SELECT SUM(amount) FROM Transactions WHERE type = :type")
    fun getTotalAmountByType(type: TransactionType): Float

}