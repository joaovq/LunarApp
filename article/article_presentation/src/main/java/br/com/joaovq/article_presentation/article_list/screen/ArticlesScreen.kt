package br.com.joaovq.article_presentation.article_list.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.joaovq.article_domain.model.Article
import br.com.joaovq.article_presentation.article_list.component.article_list.LazyArticlesList
import br.com.joaovq.article_presentation.article_list.component.article_list.ShimmerArticleList
import br.com.joaovq.ui.theme.LunarTheme
import kotlinx.coroutines.flow.flowOf
import br.com.joaovq.ui.R as CoreRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesScreen(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClickArticleCard: (Int) -> Unit = {},
    onBookmarkChanged: (Boolean, Int) -> Unit = { _, _ -> },
    onClickMenu: () -> Unit = {}
) {
    val lazyListState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .semantics {
                contentDescription = "ArticlesScreen"
                testTag = "ArticlesScreen"
            },
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.semantics {
                    contentDescription = "TopAppBar"
                    testTag = "TopAppBar"
                },
                actions = {
                    IconButton(onClick = onClickMenu) {
                        Icon(
                            imageVector = Icons.Default.FilterAlt,
                            contentDescription = "Filter Icon app"
                        )
                    }
                },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(CoreRes.string.app_name),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                },
                windowInsets = WindowInsets(
                    top = 0.dp,
                    bottom = 0.dp
                ),
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    scrolledContainerColor = MaterialTheme.colorScheme.surface,
                )
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
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    articles = articles,
                    onClickArticleCard = onClickArticleCard,
                    onBookmarkChanged = { isBookmarked, id ->
                        onBookmarkChanged(isBookmarked, id)
                    },
                    lazyListState = lazyListState
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