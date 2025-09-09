package br.com.joaovq.lunarappcompose.featured.presentation.screen

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.joaovq.article_domain.model.Article
import br.com.joaovq.article_presentation.article_list.component.article_list.LazyArticlesList
import br.com.joaovq.article_presentation.article_list.component.article_list.ShimmerArticleList
import br.com.joaovq.lunarappcompose.R
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeaturedArticlesScreen(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClickArticleCard: (Int) -> Unit = {},
) {
    Box(modifier = modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FeaturedIcon()
                            Text(stringResource(R.string.featured_articles_title))
                        }
                    },
                    windowInsets = WindowInsets(
                        top = 0.dp,
                        bottom = 0.dp
                    ),
                )
            }
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
                        modifier = Modifier,
                        articles = articles,
                        onClickArticleCard = onClickArticleCard
                    )
                }
            }
        }
    }
}

@Composable
private fun FeaturedIcon() {
    val infiniteTransition = rememberInfiniteTransition(label = "featured_icon_transition")
    val scale by infiniteTransition.animateFloat(
        initialValue = .8f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "featured_icon_scale"
    )
    Icon(
        modifier = Modifier.scale(scale),
        painter = painterResource(id = R.drawable.ic_star),
        contentDescription = null
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun FeaturedArticlesScreenPreview() {
    val articles = flowOf(
        PagingData.from(
            listOf(
                Article(
                    authors = emptyList(),
                    events = emptyList(),
                    featured = true,
                    id = 1,
                    imageUrl = "",
                    launches = emptyList(),
                    newsSite = "newsSite",
                    publishedAt = "publishedAt",
                    summary = "summary",
                    title = "title",
                    updatedAt = "updatedAt",
                    isBookmark = false,
                    url = "url"
                )
            )
        )
    ).collectAsLazyPagingItems()
    FeaturedArticlesScreen(articles = articles)
}


