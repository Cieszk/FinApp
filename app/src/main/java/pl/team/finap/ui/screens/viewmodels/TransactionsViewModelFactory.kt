package pl.team.finap.ui.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pl.team.finap.database.respository.TransactionsRepository

class TransactionsViewModelFactory(private val transactionsRepository: TransactionsRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionsViewModel::class.java)) {
            return TransactionsViewModel(transactionsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}