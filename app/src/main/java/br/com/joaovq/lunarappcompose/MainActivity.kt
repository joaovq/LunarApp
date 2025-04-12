package br.com.joaovq.lunarappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.joaovq.lunarappcompose.presentation.screen.ArticlesScreen
import br.com.joaovq.lunarappcompose.presentation.viewmodel.ArticlesViewModel
import br.com.joaovq.lunarappcompose.ui.theme.LunarAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val articlesViewModel: ArticlesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LunarAppComposeTheme {
                val articles = articlesViewModel.articles.collectAsLazyPagingItems()
                ArticlesScreen(
                    modifier = Modifier.fillMaxSize(),
                    articles = articles
                )
            }
        }
    }
}