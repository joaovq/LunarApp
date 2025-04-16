package br.com.joaovq.lunarappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.joaovq.lunarappcompose.presentation.onboarding.nav.Onboarding
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
                    NavHost(navController = navController, startDestination = Onboarding) {
                        composable<Onboarding> {
                            OnboardingScreen()
                        }
                    }
                }
            }
        }
    }
}