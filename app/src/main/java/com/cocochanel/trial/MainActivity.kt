package com.cocochanel.trial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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


val LocalNavController = compositionLocalOf<NavController> { error("No NAv Con") }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            TrialTheme {
                Scaffold { ip ->
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
}

@Composable
private fun MainContent() {
    val navController1 = rememberNavController()



    CompositionLocalProvider(LocalNavController provides navController1) {
        NavHost(navController = navController1, startDestination = "landing") {
            composable("landing") {
                CairoTimesLanding()
            }
            composable("page2") {
                LoginPage()
            }
            composable("page3") {
                NewsPageScreen()
            }// Add this route to your NavHost
            composable(
                route = "newsdetailsScreen/{articleJson}",
                arguments = listOf(navArgument("articleJson") { type = NavType.StringType })
            ) { backStackEntry ->
                val articleJson = backStackEntry.arguments?.getString("articleJson") ?: ""
                newsdetailsScreen(articleJson)
            }
        }
    }
}