package pl.team.finap.database.respository

import pl.team.finap.database.dao.WalletDao
import pl.team.finap.database.entities.TransactionType
import pl.team.finap.database.entities.Wallet
import java.math.BigDecimal

class WalletRepository(private val walletDao: WalletDao) {

    fun getOnlyWallet(): Wallet? {
        return walletDao.getOnlyWallet()
    }

    suspend fun insertInitialWalletIfNoneExists(name: String, initialBalance: BigDecimal) {
        if (getOnlyWallet() == null) {
            val wallet = Wallet(name = name, balance = initialBalance)
            walletDao.insertWallet(wallet)
        }
    }

    fun getWalletBalance(): Float {
        val income = walletDao.getTotalAmountByType(TransactionType.INCOME)
        val expenses = walletDao.getTotalAmountByType(TransactionType.EXPENSE)
        return income - expenses
    }
}