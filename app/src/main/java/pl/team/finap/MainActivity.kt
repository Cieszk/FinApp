package pl.team.finap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.team.finap.ui.screens.MainScreen
import pl.team.finap.ui.screens.NewTransactionScreen
import pl.team.finap.ui.theme.FinAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AppNavigator(navController = navController)
        }
    }
}

@Composable
fun AppNavigator(navController: NavHostController) {
    NavHost(navController, startDestination = "MainScreen") {
        composable("MainScreen") {
            MainScreen(navController)
        }
        composable("NewTransaction") {
            NewTransactionScreen()
        }
    }
}