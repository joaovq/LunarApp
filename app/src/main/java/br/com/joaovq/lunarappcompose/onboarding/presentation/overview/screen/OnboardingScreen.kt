package br.com.joaovq.lunarappcompose.onboarding.presentation.overview.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.joaovq.lunarappcompose.article.presentation.article_list.nav.ArticlesRoute
import br.com.joaovq.lunarappcompose.article.presentation.article_list.screen.ArticlesScreen
import br.com.joaovq.lunarappcompose.article.presentation.article_list.viewmodel.ArticlesViewModel
import br.com.joaovq.lunarappcompose.bookmark.presentation.nav.ArticlesBookmarkRoute
import br.com.joaovq.lunarappcompose.bookmark.presentation.screen.ArticlesBookmarkedScreen
import br.com.joaovq.lunarappcompose.bookmark.presentation.viewmodel.ArticlesBookmarkViewModel
import br.com.joaovq.lunarappcompose.onboarding.presentation.overview.component.OnboardingBottomNavigation
import br.com.joaovq.lunarappcompose.onboarding.presentation.search.nav.SearchRoute
import br.com.joaovq.lunarappcompose.onboarding.presentation.search.screen.SearchScreen
import br.com.joaovq.lunarappcompose.onboarding.presentation.search.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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
                val sheetState = rememberModalBottomSheetState()
                val scope = rememberCoroutineScope()
                var showBottomSheet by remember { mutableStateOf(false) }
                Box(modifier = Modifier.fillMaxSize()) {
                    ArticlesScreen(
                        articles = articles,
                        onClickArticleCard = onNavigateToArticle,
                        onBookmarkChanged = articlesViewModel::onBookmarkChanged,
                        onClickMenu = {
                            scope.launch {
                               try {
                                   showBottomSheet = true
                                   sheetState.show()
                               } catch (e: Exception) {
                                   e.printStackTrace()
                               }
                            }
                        }
                    )
                    if(showBottomSheet) {
                        ModalBottomSheet(
                            sheetState = sheetState,
                            onDismissRequest = {
                                scope.launch {
                                    try {
                                        sheetState.hide()
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }.invokeOnCompletion {
                                    if(!sheetState.isVisible) {
                                        showBottomSheet = false
                                    }
                                }
                            },
                        ) {
                            Text("Modal sheet")
                        }
                    }
                }
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