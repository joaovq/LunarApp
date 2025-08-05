@file:OptIn(ExperimentalMaterial3Api::class)

package br.com.joaovq.article_presentation.article_list.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.joaovq.article_domain.model.Article
import br.com.joaovq.article_presentation.R
import br.com.joaovq.article_presentation.article_list.viewmodel.ArticleViewModel
import br.com.joaovq.ui.theme.LunarTheme
import br.com.joaovq.common.utils.ext.toLocalDateTimeFormatted
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
    article?.let { articleFounded ->
        ArticleScreen(
            modifier = modifier,
            onNavigateUp = onNavigateUp,
            article = articleFounded,
            isLoading = isLoading
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArticleScreen(
    modifier: Modifier = Modifier,
    article: Article,
    isLoading: Boolean = false,
    onNavigateUp: () -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    val menuItemData = List(4) { "Option ${it + 1}" }
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Outlined.PlayCircle, contentDescription = null)
                    }
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Outlined.Share, contentDescription = null)
                    }
                    Box(modifier = Modifier) {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            menuItemData.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {}
                                )
                            }
                        }
                    }
                },
                title = {},
                windowInsets = WindowInsets(
                    top = 0.dp,
                    bottom = 0.dp
                )
            )
        }
    ) { innerPadding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize().padding(top = innerPadding.calculateTopPadding()),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState()).padding(innerPadding)
            ) {
                Text(
                    modifier = Modifier.padding(end = 16.dp, start = 16.dp, top = 8.dp),
                    text = article.title.uppercase(),
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                val asyncImagePainter = rememberAsyncImagePainter(
                    model = article.imageUrl,
                    error = painterResource(R.drawable.ic_launcher_background)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = buildAnnotatedString {
                            article.publishedAt.toLocalDateTimeFormatted()
                                ?.let { publishedAtFormatted ->
                                    append(publishedAtFormatted)
                                }
                        },
                        maxLines = 1,
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            modifier = Modifier.wrapContentHeight(Alignment.CenterVertically),
                            text = buildAnnotatedString {
                                withLink(LinkAnnotation.Url(article.url)) {
                                    append(stringResource(R.string.open_in_browser))
                                    appendInlineContent("open-in-new-icon")
                                }
                            },
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                            inlineContent = mapOf(
                                "open-in-new-icon" to InlineTextContent(
                                    placeholder = Placeholder(
                                        width = 20.sp,
                                        height = 20.sp,
                                        placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                                    ),
                                    children = {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Default.OpenInNew,
                                            contentDescription = null
                                        )
                                    }
                                )
                            )
                        )
                    }
                }
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 220.dp, max = 250.dp)
                        .padding(vertical = 8.dp),
                    painter = asyncImagePainter,
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = article.summary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewArticleScreen() {
    LunarTheme {
        ArticleScreen(
            article = Article(
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
}