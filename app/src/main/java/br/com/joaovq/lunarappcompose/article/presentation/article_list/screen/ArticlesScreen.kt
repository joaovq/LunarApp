package br.com.joaovq.lunarappcompose.article.presentation.article_list.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.joaovq.lunarappcompose.R
import br.com.joaovq.lunarappcompose.article.domain.model.Article
import br.com.joaovq.lunarappcompose.article.presentation.article_list.component.LazyArticlesList
import br.com.joaovq.lunarappcompose.article.presentation.article_list.component.ShimmerArticleList
import br.com.joaovq.lunarappcompose.ui.theme.LunarTheme
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesScreen(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClickArticleCard: (Int) -> Unit = {},
    onBookmarkChanged: (Boolean, Int) -> Unit = { _, _ -> },
    onClickMenu: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = onClickMenu) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Icon app"
                        )
                    }
                },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.app_name),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
            isRefreshing = articles.loadState.refresh is LoadState.Loading,
            onRefresh = { articles.refresh() },
        ) {
            when (articles.loadState.refresh) {
                is LoadState.Loading -> ShimmerArticleList()
                else -> LazyArticlesList(
                    articles = articles,
                    onClickArticleCard = onClickArticleCard,
                    onBookmarkChanged = onBookmarkChanged
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewArticlesScreen() {
    LunarTheme(dynamicColor = false) {
        ArticlesScreen(articles = flowOf(PagingData.from(listOf<Article>())).collectAsLazyPagingItems())
    }
}