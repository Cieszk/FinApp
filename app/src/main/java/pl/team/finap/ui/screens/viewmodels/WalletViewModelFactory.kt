package pl.team.finap.ui.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pl.team.finap.database.respository.WalletRepository

class WalletViewModelFactory(private val walletRepository: WalletRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WalletViewModel::class.java)) {
            return WalletViewModel(walletRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
