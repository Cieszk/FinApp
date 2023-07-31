package pl.team.finap.database.dao

import androidx.room.*
import pl.team.finap.database.entities.Transactions

@Dao
interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transactions): Long

    @Update
    suspend fun updateTransaction(transaction: Transactions)

    @Delete
    suspend fun deleteTransaction(transaction: Transactions)

    @Query("SELECT * FROM TRANSACTIONS")
    suspend fun getAllTransactions(): List<Transactions>

    @Query("SELECT * FROM TRANSACTIONS WHERE transaction_id = :id")
    suspend fun getTransactionById(id: Int): Transactions
}