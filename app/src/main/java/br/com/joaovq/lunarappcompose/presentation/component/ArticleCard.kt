package br.com.joaovq.lunarappcompose.presentation.component

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import br.com.joaovq.lunarappcompose.R
import br.com.joaovq.lunarappcompose.data.network.dto.ArticleDto
import br.com.joaovq.lunarappcompose.data.network.dto.Author
import br.com.joaovq.lunarappcompose.data.network.dto.Socials
import br.com.joaovq.lunarappcompose.ui.theme.LunarTheme
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: ArticleDto
) {
    Card(
        modifier = modifier.padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = article.title,
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
                                onTap = {
                                    // TODO add bookmark action
                                    // TODO open dialog after save bookmark and permit user add note and rank with rating bar
                                }
                            )
                        },
                        painter = painterResource(R.drawable.ic_bookmark),
                        contentDescription = null
                    )
                }
                Text(
                    text = article.summary,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 4,
                    textAlign = TextAlign.Justify,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

fun String.toLocalDateTimeFormatted(): String? {
    try {
        val publishedAt = OffsetDateTime.parse(this)
        val publishedAtFormatted = DateTimeFormatter.ofPattern("dd MMMM yyyy").format(publishedAt)
        return publishedAtFormatted
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}

@Preview
@PreviewLightDark
@Composable
private fun PreviewArticleCard() {
    LunarTheme(dynamicColor = false) {
        ArticleCard(
            article = ArticleDto(
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