package br.com.joaovq.lunarappcompose.presentation.articles.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import br.com.joaovq.lunarappcompose.domain.articles.model.Article
import br.com.joaovq.lunarappcompose.ui.theme.LunarTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun LazyArticlesList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(articles.itemCount, key = articles.itemKey()) { i ->
            val article = articles[i] ?: return@items
            ArticleCard(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                article = article
            )
        }
        item {
            when {
                articles.loadState.append.endOfPaginationReached -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No more articles to load")
                    }
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

@Preview
@Composable
private fun PreviewLazyArticlesList() {
    LunarTheme(dynamicColor = false) {
        LazyArticlesList(
            articles = flowOf(PagingData.from(listOf<Article>())).collectAsLazyPagingItems()
        )
    }
}