package br.com.joaovq.lunarappcompose.onboarding.presentation.search.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.joaovq.lunarappcompose.R
import br.com.joaovq.lunarappcompose.article.domain.model.Article
import br.com.joaovq.lunarappcompose.article.presentation.article_list.component.LazyArticlesList
import br.com.joaovq.lunarappcompose.article.presentation.article_list.component.ShimmerArticleList
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    query: String = "",
    onQueryChanged: (String) -> Unit = {},
    onClickArticleCard: (Int) -> Unit = {},
    onBookmarkChanged: (Boolean, Int) -> Unit = { _, _ -> }
) {
    Scaffold(modifier = modifier) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_search_tiny_stroke),
                        contentDescription = null
                    )
                },
                placeholder = {
                    Text("Search for some article...")
                },
                value = query,
                onValueChange = onQueryChanged,
                maxLines = 1,
                shape = RoundedCornerShape(10.dp)
            )
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
private fun PreviewSearchScreen() {
    SearchScreen(articles = flowOf(PagingData.from(listOf<Article>())).collectAsLazyPagingItems())
}