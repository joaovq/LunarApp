package br.com.joaovq.lunarappcompose.presentation.articles.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerArticleList(modifier: Modifier = Modifier, count: Int = 5) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(count) {
            ArticleCardShimmerItem(
                modifier = Modifier.padding(
                    PaddingValues(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
                )
            )
        }
    }
}