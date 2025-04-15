package br.com.joaovq.lunarappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.joaovq.lunarappcompose.presentation.articles.nav.Articles
import br.com.joaovq.lunarappcompose.presentation.articles.screen.ArticlesScreen
import br.com.joaovq.lunarappcompose.presentation.articles.viewmodel.ArticlesViewModel
import br.com.joaovq.lunarappcompose.ui.theme.LunarTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LunarTheme(dynamicColor = false) {
                val navController = rememberNavController()
                Surface {
                    NavHost(navController = navController, startDestination = Articles) {
                        composable<Articles> {
                            val articlesViewModel: ArticlesViewModel = hiltViewModel()
                            val articles = articlesViewModel.articles.collectAsLazyPagingItems()
                            ArticlesScreen(articles = articles)
                        }
                    }
                }
            }
        }
    }
}