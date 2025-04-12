package br.com.joaovq.lunarappcompose.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import br.com.joaovq.lunarappcompose.data.network.dto.ArticleDto
import br.com.joaovq.lunarappcompose.presentation.component.ArticleCard
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesScreen(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<ArticleDto>,
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            isRefreshing = false,
            onRefresh = { articles.refresh() },
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(articles.itemCount, key = articles.itemKey()) { i ->
                    val article = articles[i] ?: return@items
                    ArticleCard(article = article)
                }
                item {
                    when {
                        articles.loadState.append.endOfPaginationReached -> {
                            Text(text = "No more articles to load")
                        }

                        articles.loadState.append is LoadState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        articles.loadState.hasError -> Text(text = "Error to load articles")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewArticlesScreen() {
    ArticlesScreen(articles = flowOf(PagingData.from(listOf<ArticleDto>())).collectAsLazyPagingItems())
}