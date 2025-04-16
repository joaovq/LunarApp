package br.com.joaovq.lunarappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.joaovq.lunarappcompose.presentation.articles.nav.ArticleRoute
import br.com.joaovq.lunarappcompose.presentation.articles.screen.ArticleScreen
import br.com.joaovq.lunarappcompose.presentation.articles.viewmodel.ArticleViewModel
import br.com.joaovq.lunarappcompose.presentation.onboarding.nav.OnboardingRoute
import br.com.joaovq.lunarappcompose.presentation.onboarding.screen.OnboardingScreen
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
                    NavHost(navController = navController, startDestination = OnboardingRoute) {
                        composable<OnboardingRoute> {
                            OnboardingScreen(
                                onNavigateToArticle = { articleId ->
                                    navController.navigate(ArticleRoute(articleId))
                                }
                            )
                        }
                        composable<ArticleRoute> {
                            val articleViewModel = hiltViewModel<ArticleViewModel>()
                            val article by articleViewModel.article.collectAsStateWithLifecycle()
                            val isLoading by articleViewModel.isLoading.collectAsStateWithLifecycle()
                            article?.let { articleFounded ->
                                ArticleScreen(
                                    onNavigateUp = navController::navigateUp,
                                    article = articleFounded,
                                    isLoading = isLoading
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}