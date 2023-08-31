package pl.team.finap.ui.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import pl.team.finap.database.respository.WalletRepository

class WalletViewModel(private val walletRepository: WalletRepository) : ViewModel() {


    val walletBalance = liveData {
        emit(walletRepository.getWalletBalance())
    }

}