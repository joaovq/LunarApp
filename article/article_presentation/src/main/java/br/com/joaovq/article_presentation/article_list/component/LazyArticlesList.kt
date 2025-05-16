package br.com.joaovq.lunarappcompose.article.presentation.article_list.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import br.com.joaovq.lunarappcompose.core.utils.mocks.PreviewMockObjects
import br.com.joaovq.article_domain.model.Article
import br.com.joaovq.lunarappcompose.ui.theme.LocalDimen
import br.com.joaovq.lunarappcompose.ui.theme.LunarColors
import br.com.joaovq.lunarappcompose.ui.theme.LunarTheme
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@Composable
fun LazyArticlesList(
    modifier: Modifier = Modifier,
    isReturnToTopEnable: Boolean = true,
    articles: LazyPagingItems<br.com.joaovq.article_domain.model.Article>,
    onClickArticleCard: (Int) -> Unit,
    onBookmarkChanged: (Boolean, Int) -> Unit = { _, _ -> }
) {
    val dimen = LocalDimen.current
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val firstItemIsVisible by remember { derivedStateOf { lazyListState.firstVisibleItemIndex == 0 } }
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize(), state = lazyListState) {
            items(articles.itemCount, key = articles.itemKey()) { i ->
                val article = articles[i] ?: return@items
                ArticleCard(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    article = article,
                    onClickArticleCard = onClickArticleCard,
                    onBookmarkChanged = onBookmarkChanged
                )
            }
            item {
                when {
                    articles.loadState.append.endOfPaginationReached -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No more articles to load",
                                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                            )
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

                    articles.loadState.hasError -> {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Error to load articles"
                        )
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = !firstItemIsVisible && isReturnToTopEnable,
            modifier = Modifier
                .padding(dimen.large)
                .shadow(4.dp, shape = CircleShape)
                .align(Alignment.BottomEnd)
        ) {
            IconButton(
                modifier = Modifier
                    .background(
                        if (isSystemInDarkTheme()) {
                            LunarColors.bottomNavigationBackgroundDark
                        } else {
                            LunarColors.bottomNavigationBackgroundLight
                        }
                    )
                    .clip(CircleShape),
                onClick = {
                    scope.launch {
                        try {
                            lazyListState.animateScrollToItem(0)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            ) {
                Icon(Icons.Default.ArrowUpward, contentDescription = "Scroll to top")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLazyArticlesList() {
    LunarTheme(dynamicColor = false) {
        LazyArticlesList(
            articles = flowOf(
                PagingData.from(listOf(PreviewMockObjects.article))
            ).collectAsLazyPagingItems(),
            onClickArticleCard = {}
        )
    }
}