package pl.team.finap.database.respository

import pl.team.finap.database.dao.TransactionsDao
import pl.team.finap.database.dao.WalletDao
import pl.team.finap.database.entities.CategoryType
import pl.team.finap.database.entities.TransactionType
import pl.team.finap.database.entities.Transactions

class TransactionsRepository(
    private val transactionsDao: TransactionsDao,
    private val walletDao: WalletDao
) {

    suspend fun insertTransaction(transaction: Transactions): Long {
        return transactionsDao.insertTransaction(transaction)
    }

    suspend fun updateTransaction(transaction: Transactions) {
        transactionsDao.updateTransaction(transaction)
    }

    suspend fun deleteTransaction(transaction: Transactions) {
        transactionsDao.deleteTransaction(transaction)
    }

    suspend fun getGlobalWalletId(): Long {
        // Assuming you want the ID of the only wallet
        return walletDao.getOnlyWallet()?.wallet_id ?: -1L
    }

    suspend fun getAllTransactions(): List<Transactions> {
        return transactionsDao.getAllTransactions()
    }

    suspend fun getTotalIncome(): Float {
        return transactionsDao.getTotalForType(TransactionType.INCOME)
    }

    suspend fun getTotalExpenses(): Float {
        return transactionsDao.getTotalForType(TransactionType.EXPENSE)
    }

    fun getTotalExpensePerCategory() = transactionsDao.getTotalExpensePerCategory()

    data class CategoryTotal(val category: CategoryType, val total: Float)

}