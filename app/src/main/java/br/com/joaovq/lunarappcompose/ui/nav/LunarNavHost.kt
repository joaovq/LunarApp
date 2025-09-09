package br.com.joaovq.lunarappcompose.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.joaovq.article_presentation.article_list.component.article.ArticlesRoot
import br.com.joaovq.article_presentation.article_list.nav.ArticleRoute
import br.com.joaovq.article_presentation.article_list.nav.ArticlesRoute
import br.com.joaovq.article_presentation.article_list.screen.ArticleRoot
import br.com.joaovq.bookmark_presentation.nav.ArticlesBookmarkRoute
import br.com.joaovq.bookmark_presentation.screen.ArticlesBookmarkedScreen
import br.com.joaovq.bookmark_presentation.viewmodel.ArticlesBookmarkViewModel
import br.com.joaovq.lunarappcompose.featured.presentation.nav.FeaturedRoute
import br.com.joaovq.lunarappcompose.featured.presentation.screen.FeaturedArticlesScreen
import br.com.joaovq.lunarappcompose.featured.presentation.viewmodel.FeaturedArticlesViewModel
import br.com.joaovq.lunarappcompose.search.nav.SearchRoute
import br.com.joaovq.lunarappcompose.search.screen.SearchScreen
import br.com.joaovq.lunarappcompose.search.viewmodel.SearchViewModel
import br.com.joaovq.ui.state.MainState

@Composable
fun LunarNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    mainState: MainState = MainState(),
    onSearchResults: (List<String>) -> Unit = {},
    onReset: () -> Unit = {},
    getInfo: () -> Unit = {},
) {
    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = ArticlesRoute
    ) {
        composable<ArticleRoute> {
            ArticleRoot(onNavigateUp = navController::navigateUp)
        }
        composable<ArticlesRoute> {
            ArticlesRoot(
                modifier,
                mainState = mainState,
                onNavigateToArticle = { navController.navigate(ArticleRoute(it)) },
                onSearchResults = onSearchResults,
                getInfo = getInfo,
                onReset = onReset
            )
        }
        composable<FeaturedRoute> {
            val viewModel: FeaturedArticlesViewModel = hiltViewModel()
            val articles = viewModel.articles.collectAsLazyPagingItems()
            FeaturedArticlesScreen(
                modifier = modifier,
                articles = articles,
                onClickArticleCard = { navController.navigate(ArticleRoute(it)) }
            )
        }
        composable<SearchRoute> {
            val searchViewModel: SearchViewModel = hiltViewModel()
            val articles = searchViewModel.articles.collectAsLazyPagingItems()
            val query by searchViewModel.query.collectAsStateWithLifecycle()
            SearchScreen(
                modifier,
                query = query.orEmpty(),
                articles = articles,
                onQueryChanged = searchViewModel::onQueryChanged,
                onClickArticleCard = { navController.navigate(ArticleRoute(it)) }
            )
        }
        composable<ArticlesBookmarkRoute> {
            val viewModel: ArticlesBookmarkViewModel = hiltViewModel()
            val articles by viewModel.bookmarks.collectAsStateWithLifecycle()
            ArticlesBookmarkedScreen(
                modifier,
                articles = articles,
                onClickArticleCard = { navController.navigate(ArticleRoute(it)) }
            )
        }
    }
}