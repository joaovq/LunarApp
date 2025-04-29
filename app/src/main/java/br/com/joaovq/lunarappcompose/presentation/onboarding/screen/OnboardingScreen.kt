package br.com.joaovq.lunarappcompose.presentation.onboarding.screen

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.joaovq.lunarappcompose.presentation.articles.nav.ArticlesRoute
import br.com.joaovq.lunarappcompose.presentation.articles.screen.ArticlesScreen
import br.com.joaovq.lunarappcompose.presentation.articles.viewmodel.ArticlesViewModel
import br.com.joaovq.lunarappcompose.presentation.bookmark.nav.ArticlesBookmarkRoute
import br.com.joaovq.lunarappcompose.presentation.bookmark.screen.ArticlesBookmarkedScreen
import br.com.joaovq.lunarappcompose.presentation.bookmark.viewmodel.ArticlesBookmarkViewModel
import br.com.joaovq.lunarappcompose.presentation.onboarding.component.OnboardingBottomNavigation
import br.com.joaovq.lunarappcompose.presentation.search.nav.SearchRoute
import br.com.joaovq.lunarappcompose.presentation.search.screen.SearchScreen
import br.com.joaovq.lunarappcompose.presentation.search.viewmodel.SearchViewModel

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onNavigateToArticle: (Int) -> Unit = {}
) {
    val navController = rememberNavController()
    Scaffold(
        modifier = modifier.imePadding(),
        bottomBar = { OnboardingBottomNavigation(navController = navController) }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
            navController = navController,
            startDestination = ArticlesRoute
        ) {
            composable<ArticlesRoute> {
                val articlesViewModel: ArticlesViewModel = hiltViewModel()
                val articles = articlesViewModel.articles.collectAsLazyPagingItems()
                ArticlesScreen(
                    articles = articles,
                    onClickArticleCard = onNavigateToArticle,
                    onBookmarkChanged = articlesViewModel::onBookmarkChanged
                )
            }
            composable<SearchRoute> {
                val searchViewModel: SearchViewModel = hiltViewModel()
                val articles = searchViewModel.articles.collectAsLazyPagingItems()
                val query by searchViewModel.query.collectAsStateWithLifecycle()
                SearchScreen(
                    query = query.orEmpty(),
                    articles = articles,
                    onQueryChanged = searchViewModel::onQueryChanged,
                    onClickArticleCard = onNavigateToArticle
                )
            }
            composable<ArticlesBookmarkRoute> {
                val viewModel: ArticlesBookmarkViewModel = hiltViewModel()
                val articles by viewModel.bookmarks.collectAsStateWithLifecycle()
                ArticlesBookmarkedScreen(
                    articles = articles,
                    onClickArticleCard = onNavigateToArticle
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    OnboardingScreen()
}