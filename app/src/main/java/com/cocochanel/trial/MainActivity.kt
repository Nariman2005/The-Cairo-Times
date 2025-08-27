package com.cocochanel.trial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cocochanel.trial.ui.features.landingpage.CairoTimesLanding
import com.cocochanel.trial.ui.features.loginpage.LoginPage
import com.cocochanel.trial.ui.features.newsdetails.newsdetailsScreen
import com.cocochanel.trial.ui.features.newspage.NewsPageScreen
import com.cocochanel.trial.ui.theme.TrialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent()
                }
            }
        }
    }
}

@Composable
private fun MainContent() {
    val navController1 = rememberNavController()



    NavHost(navController = navController1, startDestination = "landing") {
        composable("landing") {
            CairoTimesLanding(navController1)
        }
        composable("page2") {
            LoginPage(navController1)
        }
        composable("page3") {
            NewsPageScreen(navController1)
        }// Add this route to your NavHost
        composable(
            route = "news_details/{articleJson}",
            arguments = listOf(navArgument("articleJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val articleJson = backStackEntry.arguments?.getString("articleJson") ?: ""
            newsdetailsScreen(navController1, articleJson)
        }
    }
}