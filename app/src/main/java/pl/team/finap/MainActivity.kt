package pl.team.finap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.team.finap.database.ApplicationDatabase
import pl.team.finap.database.respository.TransactionsRepository
import pl.team.finap.database.respository.WalletRepository
import pl.team.finap.ui.screens.MainScreen
import pl.team.finap.ui.screens.viewmodels.NewTransactionScreen
import java.math.BigDecimal

class MainActivity : ComponentActivity() {
    private lateinit var transactionsRepository: TransactionsRepository
    private lateinit var walletRepository: WalletRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDatabase()

        setContent {
            val navController = rememberNavController()
            AppNavigator(navController = navController, transactionsRepository = transactionsRepository, walletRepository = walletRepository)
        }
    }

    private fun initDatabase() {
        val database = ApplicationDatabase.getDatabase(this)
        val walletDao = database.walletDao()
        walletRepository = WalletRepository(walletDao)

        lifecycleScope.launch(Dispatchers.IO) {
            walletRepository.insertInitialWalletIfNoneExists("Main Wallet", BigDecimal(0))
        }

        val transactionsDao = database.transactionsDao()
        transactionsRepository = TransactionsRepository(transactionsDao, walletDao)
    }
}

@Composable
fun AppNavigator(navController: NavHostController, transactionsRepository: TransactionsRepository, walletRepository: WalletRepository) {
    NavHost(navController, startDestination = "MainScreen") {
        composable("MainScreen") {
            MainScreen(navController = navController, transactionsRepository = transactionsRepository, walletRepository = walletRepository)
        }
        composable("NewTransaction") {
            NewTransactionScreen(navController = navController, transactionsRepository = transactionsRepository)
        }
    }
}