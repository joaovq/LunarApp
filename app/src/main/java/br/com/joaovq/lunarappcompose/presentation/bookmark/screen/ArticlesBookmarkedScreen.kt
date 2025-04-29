package br.com.joaovq.lunarappcompose.presentation.bookmark.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.joaovq.lunarappcompose.domain.articles.model.Article
import br.com.joaovq.lunarappcompose.presentation.articles.component.ArticleCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesBookmarkedScreen(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onClickArticleCard: (Int) -> Unit = {}
) {
    val lazyListState = rememberLazyListState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("my bookmarks")
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
            state = lazyListState
        ) {
            items(articles) { article ->
                ArticleCard(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    article = article,
                    onClickArticleCard = onClickArticleCard,
                )
            }
        }
    }
}