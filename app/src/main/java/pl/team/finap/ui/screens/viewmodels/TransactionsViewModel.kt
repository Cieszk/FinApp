package pl.team.finap.ui.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import pl.team.finap.database.entities.Transactions
import pl.team.finap.database.respository.TransactionsRepository

class TransactionsViewModel(private val transactionsRepository: TransactionsRepository) :
    ViewModel() {

    private val _expensePerCategory =
        MutableStateFlow<List<TransactionsRepository.CategoryTotal>>(emptyList())
    val expensePerCategory: StateFlow<List<TransactionsRepository.CategoryTotal>> =
        _expensePerCategory

    private val _transactions = MutableStateFlow<List<Transactions>>(emptyList())
    val transactions: StateFlow<List<Transactions>> = _transactions

    init {
        viewModelScope.launch {
            _expensePerCategory.value = transactionsRepository.getTotalExpensePerCategory().first()
            _transactions.value = transactionsRepository.getAllTransactions()
        }
    }

    fun insertTransaction(transaction: Transactions) = viewModelScope.launch {
        transactionsRepository.insertTransaction(transaction)
        _transactions.value = transactionsRepository.getAllTransactions()
    }

    fun getGlobalWalletId(): Long {
        var walletId = -1L
        viewModelScope.launch {
            walletId = transactionsRepository.getGlobalWalletId()
        }
        return walletId
    }

    fun refreshTransactions() {
        viewModelScope.launch {
            _expensePerCategory.value = transactionsRepository.getTotalExpensePerCategory().first()
        }
    }

    val totalIncome = liveData {
        emit(transactionsRepository.getTotalIncome())
    }

    val totalExpenses = liveData {
        emit(transactionsRepository.getTotalExpenses())
    }
}