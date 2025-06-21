package br.com.joaovq.lunarappcompose

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import br.com.joaovq.article_data.service.ArticleBookmarkSyncWork
import br.com.joaovq.ui.theme.LunarTheme
import br.com.joaovq.data.service.SyncWork
import br.com.joaovq.lunarappcompose.ui.LunarApp
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
                LunarApp()
            }
        }
    }
}