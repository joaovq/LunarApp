package br.com.joaovq.lunarappcompose.article.presentation.article_list.component

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import br.com.joaovq.lunarappcompose.R
import br.com.joaovq.lunarappcompose.article.data.network.dto.Author
import br.com.joaovq.lunarappcompose.article.data.network.dto.Socials
import br.com.joaovq.lunarappcompose.article.domain.model.Article
import br.com.joaovq.lunarappcompose.core.ui.utils.ext.shimmerEffect
import br.com.joaovq.lunarappcompose.core.utils.ext.toLocalDateTimeFormatted
import br.com.joaovq.lunarappcompose.ui.theme.LocalDimen
import br.com.joaovq.lunarappcompose.ui.theme.LunarTheme
import br.com.joaovq.lunarappcompose.ui.theme.Obsidian
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import java.time.OffsetDateTime

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClickArticleCard: (Int) -> Unit = {},
    onBookmarkChanged: (Boolean, Int) -> Unit = { _, _ -> }
) {
    val dimen = LocalDimen.current
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) Obsidian else Color.White,
            contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black
        ),
        onClick = { onClickArticleCard(article.id) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimen.large)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimen.medium)
            ) {
                val imagePainter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(article.imageUrl)
                        .build(),
                    error = painterResource(R.drawable.ic_launcher_background),
                    onLoading = { _ ->
                    }
                )
                val imagePainterState by imagePainter.state.collectAsState()
                when (imagePainterState) {
                    is AsyncImagePainter.State.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_article_card)))
                                .shimmerEffect(),
                        )
                    }

                    is AsyncImagePainter.State.Empty -> Unit
                    else -> {
                        Image(
                            painter = imagePainter,
                            contentDescription = "Article ${article.id} image",
                            modifier = Modifier
                                .heightIn(max = 150.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_article_card))),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
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
                            append(article.authors.joinToString { it.name })
                            article.publishedAt.toLocalDateTimeFormatted()
                                ?.let { publishedAtFormatted ->
                                    append(" - ")
                                    append(publishedAtFormatted)
                                }
                        },
                        maxLines = 4,
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                    )
                    Icon(
                        modifier = Modifier.pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    onBookmarkChanged(article.isBookmark, article.id)
                                }
                            )
                        },
                        painter = if (article.isBookmark) painterResource(R.drawable.ic_bookmark_filled) else painterResource(
                            R.drawable.ic_bookmark
                        ),
                        contentDescription = null
                    )
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