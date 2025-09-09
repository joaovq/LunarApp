package br.com.joaovq.article_presentation.article_list.screen

import android.content.Intent
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.palette.graphics.Palette
import br.com.joaovq.article_domain.model.Article
import br.com.joaovq.article_presentation.R
import br.com.joaovq.article_presentation.article_list.component.article.ArticleBottomSheetContent
import br.com.joaovq.article_presentation.article_list.component.article.ArticlePreviewParameterProvider
import br.com.joaovq.article_presentation.article_list.component.article.ArticleTopAppBarActions
import br.com.joaovq.article_presentation.article_list.component.article.ArticleTopAppBarContainer
import br.com.joaovq.article_presentation.article_list.viewmodel.ArticleViewModel
import br.com.joaovq.ui.theme.LocalDimen
import br.com.joaovq.ui.theme.LunarTheme
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.toBitmap


@Composable
fun ArticleRoot(
    modifier: Modifier = Modifier,
    articleViewModel: ArticleViewModel = hiltViewModel<ArticleViewModel>(),
    onNavigateUp: () -> Unit = {},
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
    val dimen = LocalDimen.current
    var expanded by remember { mutableStateOf(false) }
    var topBarContrastColor by remember { mutableStateOf(Color.White) }
    val context = LocalContext.current
    val windowInfo = LocalWindowInfo.current
    BottomSheetScaffold(
        modifier = modifier,
        sheetShape = RectangleShape,
        sheetPeekHeight = windowInfo.containerSize.height.dp * 0.25f,
        sheetContent = {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimen.medium),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                ArticleBottomSheetContent(article = article)
            }
        },
        sheetDragHandle = null,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = innerPadding.calculateBottomPadding().minus(dimen.medium)
                )
        ) {
            val asyncImagePainter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context)
                    .data(article?.imageUrl)
                    .allowHardware(false).build(),
                error = painterResource(R.drawable.ic_launcher_background),
                contentScale = ContentScale.Crop,
                onSuccess = { state ->
                    topBarContrastColor = getContrastColor(state = state)
                }
            )
            Image(
                modifier = Modifier.fillMaxSize(),
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
                        val uriHandler = LocalUriHandler.current
                        ArticleTopAppBarActions(
                            expanded = expanded,
                            menuItemData = listOf(),
                            onClickShareIcon = {
                                article?.url?.let { sharedUri ->
                                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                        putExtra(Intent.EXTRA_TEXT, sharedUri)
                                        type = "text/*"
                                    }
                                    context.startActivity(
                                        Intent.createChooser(
                                            shareIntent,
                                            "Share url article"
                                        )
                                    )
                                }
                            },
                            onClickInternetIcon = {
                                article?.url?.let { uriHandler.openUri(it) }
                            },
                            onClickMoreVert = { expanded = !expanded },
                            onDismissRequest = { expanded = false }
                        )
                    }
                )
                Text(
                    modifier = Modifier
                        .padding(horizontal = dimen.large, vertical = 20.dp),
                    text = article?.title.orEmpty(),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = topBarContrastColor
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

private fun getContrastColor(state: AsyncImagePainter.State.Success): Color {
    val bitmap = state.result.image.toBitmap()
    val palette = Palette.from(bitmap)
        .clearFilters().generate()
    val dominant = palette.getDominantColor(0xFFFFFF)
    val luminance = Color(dominant).luminance()
    return if (luminance > 0.5f) {
        Color.DarkGray
    } else {
        Color.White
    }
}


@Preview(showSystemUi = true, name = "Article screen preview light theme")
@Composable
private fun PreviewArticleScreen(
    @PreviewParameter(ArticlePreviewParameterProvider::class) article: Article,
) {
    LunarTheme {
        ArticleScreen(article = article)
    }
}