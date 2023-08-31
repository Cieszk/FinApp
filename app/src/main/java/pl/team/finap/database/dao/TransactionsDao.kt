package pl.team.finap.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.team.finap.database.entities.TransactionType
import pl.team.finap.database.entities.Transactions
import pl.team.finap.database.respository.TransactionsRepository

@Dao
interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(transaction: Transactions): Long

    @Update
    fun updateTransaction(transaction: Transactions)

    @Delete
    fun deleteTransaction(transaction: Transactions)

    @Query("SELECT * FROM TRANSACTIONS WHERE wallet_id = :walletId")
    fun getTransactionsForWallet(walletId: Long): List<Transactions>

    @Query("SELECT * FROM TRANSACTIONS")
    fun getAllTransactions(): List<Transactions>

    @Query("SELECT category, SUM(amount) as total FROM transactions WHERE type = 'EXPENSE' GROUP BY category")
    fun getTotalExpensePerCategory(): Flow<List<TransactionsRepository.CategoryTotal>>

    @Query("SELECT SUM(amount) FROM transactions WHERE type = :transactionType")
    fun getTotalForType(transactionType: TransactionType): Float
}