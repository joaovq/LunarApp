package br.com.joaovq.lunarappcompose

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import br.com.joaovq.article_data.service.ArticleBookmarkSyncWork
import br.com.joaovq.article_presentation.article_list.nav.ArticleRoute
import br.com.joaovq.article_presentation.article_list.screen.ArticleScreen
import br.com.joaovq.article_presentation.article_list.viewmodel.ArticleViewModel
import br.com.joaovq.core.ui.theme.LunarTheme
import br.com.joaovq.data.service.SyncWork
import br.com.joaovq.lunarappcompose.onboarding.presentation.overview.nav.OnboardingRoute
import br.com.joaovq.lunarappcompose.onboarding.presentation.overview.screen.OnboardingScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                WorkManager.getInstance(this)
                    .beginUniqueWork(
                        SyncWork.ONE_TIME_REQUEST_TAG,
                        ExistingWorkPolicy.APPEND_OR_REPLACE,
                        SyncWork.oneTimeRequest()
                    )
                    .then(ArticleBookmarkSyncWork.oneTimeRequest())
                    .enqueue()
                Toast.makeText(
                    this,
                    "Permissão para notificação concedida",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LunarTheme(dynamicColor = false) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    resultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
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