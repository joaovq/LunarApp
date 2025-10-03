package br.com.joaovq.bookmark_presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import br.com.joaovq.article_domain.model.Article
import br.com.joaovq.article_presentation.article_list.component.article_list.ArticleCard
import br.com.joaovq.article_presentation.article_list.state.ArticleBookmarkState
import br.com.joaovq.ui.theme.LocalDimen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesBookmarkedScreen(
    modifier: Modifier = Modifier,
    state: ArticleBookmarkState,
    onClickArticleCard: (Int) -> Unit = {}
) {
    val lazyListState = rememberLazyListState()
    val dimen = LocalDimen.current
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("my bookmarks")
                },
                windowInsets = WindowInsets(
                    top = 0.dp,
                    bottom = 0.dp
                )
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.articles.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = innerPadding.calculateTopPadding()),
                state = lazyListState
            ) {
                items(state.articles) { article ->
                    ArticleCard(
                        modifier = Modifier.padding(
                            horizontal = dimen.large,
                            vertical = dimen.medium
                        ),
                        article = article,
                        onClickArticleCard = onClickArticleCard
                    )
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "No bookmarks found",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.Gray.copy(alpha = 0.8f)
                    )
                )
            }
        }
    }
}


@PreviewLightDark
@Composable
fun ArticlesBookmarkedScreenPreview(
    @PreviewParameter(ArticlesBookmarkedPreviewParameter::class) state: ArticleBookmarkState
) {
    ArticlesBookmarkedScreen(state = state)
}

class ArticlesBookmarkedPreviewParameter : PreviewParameterProvider<ArticleBookmarkState> {
    override val values: Sequence<ArticleBookmarkState> = sequenceOf(
        ArticleBookmarkState.empty(),
        ArticleBookmarkState(
            articles = listOf(
                Article(
                    id = 1,
                    title = "Article 1",
                    summary = "Summary 1",
                    imageUrl = "",
                    newsSite = "News Site 1",
                    publishedAt = "",
                    updatedAt = "",
                    url = "",
                    isBookmark = true,
                    authors = emptyList(),
                    featured = false,
                    launches = emptyList(),
                    events = emptyList()
                ),
                Article(
                    id = 2,
                    title = "Article 2",
                    summary = "Summary 2",
                    imageUrl = "",
                    newsSite = "News Site 2",
                    publishedAt = "",
                    updatedAt = "",
                    url = "",
                    isBookmark = true,
                    authors = emptyList(),
                    featured = false,
                    launches = emptyList(),
                    events = emptyList()
                )
            ),
            isLoading = false,
            error = null
        ),
        ArticleBookmarkState(
            articles = listOf(
                Article(
                    id = 1,
                    title = "Article 1",
                    summary = "Summary 1",
                    imageUrl = "",
                    newsSite = "News Site 1",
                    publishedAt = "",
                    updatedAt = "",
                    url = "",
                    isBookmark = true,
                    authors = emptyList(),
                    featured = false,
                    launches = emptyList(),
                    events = emptyList()
                ),
                Article(
                    id = 2,
                    title = "Article 2",
                    summary = "Summary 2",
                    imageUrl = "",
                    newsSite = "News Site 2",
                    publishedAt = "",
                    updatedAt = "",
                    url = "",
                    isBookmark = true,
                    authors = emptyList(),
                    featured = false,
                    launches = emptyList(),
                    events = emptyList()
                )
            ),
            isLoading = true,
            error = null
        )
    )
}