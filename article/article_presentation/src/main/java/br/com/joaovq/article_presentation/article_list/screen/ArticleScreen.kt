@file:OptIn(ExperimentalMaterial3Api::class)

package br.com.joaovq.article_presentation.article_list.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.joaovq.article_domain.model.Article
import br.com.joaovq.article_presentation.R
import br.com.joaovq.article_presentation.article_list.component.article.ArticleBottomSheetContent
import br.com.joaovq.article_presentation.article_list.component.article.ArticleTopAppBarActions
import br.com.joaovq.article_presentation.article_list.component.article.ArticleTopAppBarContainer
import br.com.joaovq.article_presentation.article_list.viewmodel.ArticleViewModel
import br.com.joaovq.ui.theme.LunarTheme
import coil3.compose.rememberAsyncImagePainter
import java.time.OffsetDateTime


@Composable
fun ArticleRoot(
    modifier: Modifier = Modifier,
    articleViewModel: ArticleViewModel = hiltViewModel<ArticleViewModel>(),
    onNavigateUp: () -> Unit = {}
) {
    val article by articleViewModel.article.collectAsStateWithLifecycle()
    val isLoading by articleViewModel.isLoading.collectAsStateWithLifecycle()
    ArticleScreen(
        modifier = modifier,
        onNavigateUp = onNavigateUp,
        article = article,
        isLoading = isLoading
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArticleScreen(
    modifier: Modifier = Modifier,
    article: Article?,
    isLoading: Boolean = false,
    onNavigateUp: () -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    val menuItemData = List(1) { "Option ${it + 1}" }
    val windowInfo = LocalWindowInfo.current
    val sheetScaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        modifier = modifier,
        sheetPeekHeight = windowInfo.containerSize.height.dp * 0.25f,
        sheetContent = {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                ArticleBottomSheetContent(article = article)
            }
        },
        scaffoldState = sheetScaffoldState
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = innerPadding
                        .calculateBottomPadding()
                        .minus(16.dp)
                )
        ) {
            val asyncImagePainter = rememberAsyncImagePainter(
                model = article?.imageUrl,
                error = painterResource(R.drawable.ic_launcher_background),
            )
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .blur(radiusX = 2.dp, radiusY = 2.dp),
                painter = asyncImagePainter,
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                ArticleTopAppBarContainer(
                    onNavigateUp = onNavigateUp,
                    actions = {
                        ArticleTopAppBarActions(
                            expanded = expanded,
                            menuItemData = menuItemData,
                            onClickMoreVert = { expanded = !expanded },
                            onDismissRequest = { expanded = false }
                        )
                    }
                )
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = article?.title?.uppercase().orEmpty(),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
    }
}

class ArticlePreviewParameterProvider : PreviewParameterProvider<Article> {
    override val values: Sequence<Article>
        get() = sequenceOf(
            Article(
                emptyList(),
                emptyList(),
                featured = false,
                1,
                "",
                emptyList(),
                "",
                OffsetDateTime.now().toString(),
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                "Solar Panels Now Capable of Generating Power from Moonlight",
                "",
                isBookmark = false,
                ""
            )
        )

}


@Preview(showSystemUi = true, name = "Article screen preview light theme")
@Composable
private fun PreviewArticleScreen(
    @PreviewParameter(ArticlePreviewParameterProvider::class) article: Article
) {
    LunarTheme {
        ArticleScreen(article = article)
    }
}