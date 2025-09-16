package br.com.joaovq.article_presentation.article_list.component.article_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import br.com.joaovq.article_domain.model.Article
import br.com.joaovq.article_domain.model.Author
import br.com.joaovq.article_domain.model.Socials
import br.com.joaovq.article_presentation.R
import br.com.joaovq.common.utils.ext.toLocalDateTimeFormatted
import br.com.joaovq.ui.theme.LocalDimen
import br.com.joaovq.ui.theme.LunarTheme
import br.com.joaovq.ui.theme.Obsidian
import br.com.joaovq.ui.utils.ext.shimmerEffect
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import java.time.OffsetDateTime
import br.com.joaovq.ui.R as CoreUiRes

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClickArticleCard: (Int) -> Unit = {},
    onBookmarkChanged: ((Boolean, Int) -> Unit)? = null
) {
    val dimen = LocalDimen.current
    var isBookmark by remember(article) { mutableStateOf(article.isBookmark) }
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) Obsidian else Color.White,
            contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black
        ),
        onClick = { onClickArticleCard(article.id) }
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimen.medium)
            ) {
                val imagePainter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(article.imageUrl)
                        .build(),
                    error = painterResource(R.drawable.ic_launcher_background),
                )
                val imagePainterState by imagePainter.state.collectAsState()
                when (imagePainterState) {
                    is AsyncImagePainter.State.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .shimmerEffect(),
                        )
                    }

                    else -> {
                        Image(
                            painter = imagePainter,
                            contentDescription = "Article ${article.id} image",
                            modifier = Modifier
                                .heightIn(max = 150.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimen.large)
                ) {
                    Text(
                        text = article.title,
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                article.authors.takeIf { it.isNotEmpty() }?.let {
                                    append(it.joinToString { author -> author.name })
                                    append(" - ")
                                }
                                article.publishedAt.toLocalDateTimeFormatted()
                                    ?.let { publishedAtFormatted ->
                                        append(publishedAtFormatted)
                                    }
                            },
                            maxLines = 4,
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                        )
                        onBookmarkChanged?.let {
                            Icon(
                                modifier = Modifier.pointerInput(Unit) {
                                    detectTapGestures(
                                        onPress = {
                                            onBookmarkChanged.invoke(!isBookmark, article.id)
                                            isBookmark = !isBookmark
                                        }
                                    )
                                },
                                painter = if (isBookmark) painterResource(CoreUiRes.drawable.ic_bookmark_filled) else painterResource(
                                    CoreUiRes.drawable.ic_bookmark
                                ),
                                contentDescription = null
                            )
                        }
                    }
                    Text(
                        text = article.summary,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 4,
                        textAlign = TextAlign.Justify,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview
@PreviewLightDark
@Composable
private fun PreviewArticleCard() {
    LunarTheme(dynamicColor = false) {
        ArticleCard(
            article = Article(
                id = 1,
                title = "Title 1",
                url = "",
                imageUrl = "",
                newsSite = "",
                summary = "Summary 1",
                publishedAt = OffsetDateTime.now().toString(),
                updatedAt = "",
                featured = false,
                launches = emptyList(),
                events = emptyList(),
                isBookmark = true,
                authors = listOf(
                    Author(
                        name = "Author 1",
                        socials = Socials(
                            "",
                            "", "",
                            "",
                            "",
                            ""
                        )
                    )
                )
            )
        )
    }
}