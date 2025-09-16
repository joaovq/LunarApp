package br.com.joaovq.article_presentation.article_list.component.article

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import br.com.joaovq.article_domain.model.Article
import br.com.joaovq.article_domain.model.Author
import br.com.joaovq.article_presentation.R
import br.com.joaovq.common.utils.ext.toLocalDateTimeFormatted
import br.com.joaovq.ui.theme.LocalDimen
import java.time.OffsetDateTime

@Composable
fun ArticleInfoBody(
    modifier: Modifier = Modifier,
    article: Article?,
) {
    val dimen = LocalDimen.current
    LazyColumn(modifier = modifier) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = dimen.large, end = dimen.large, top = dimen.large),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = buildAnnotatedString {
                        article?.publishedAt?.toLocalDateTimeFormatted()
                            ?.let { publishedAtFormatted ->
                                append(publishedAtFormatted)
                            }
                    },
                    maxLines = 1,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )
            }
        }
        item {
            article?.summary?.let { summaryText ->
                Spacer(modifier = Modifier.height(dimen.medium))
                Card(modifier = Modifier.padding(horizontal = dimen.large)) {
                    Column(modifier = Modifier.padding(dimen.medium)) {
                        Text(
                            text = stringResource(R.string.summary_title_article),
                            maxLines = 1,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Spacer(modifier = Modifier.height(dimen.medium))
                        Text(
                            text = summaryText,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(dimen.medium))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimen.large, vertical = dimen.large),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    dimen.large,
                    alignment = Alignment.Start
                )
            ) {
                article?.authors?.takeIf { it.isNotEmpty() }?.let {
                    Card(modifier = Modifier.weight(1f)) {
                        Column(modifier = Modifier.padding(dimen.medium)) {
                            Text(
                                stringResource(R.string.authors_title_article),
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                            )
                            Text(
                                text = it.joinToString(", ") { author -> author.name },
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
                article?.newsSite?.let {
                    Card(modifier = Modifier.weight(1f)) {
                        Column(modifier = Modifier.padding(dimen.medium)) {
                            Text(
                                stringResource(R.string.site_title_article),
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                            )
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
        item {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
        }
    }
}

@PreviewLightDark
@Composable
fun ArticleBottomSheetContentPreview() {
    val article = Article(
        listOf(Author("NASA", null)),
        emptyList(),
        featured = false,
        1,
        "",
        emptyList(),
        "NASA",
        OffsetDateTime.now().toString(),
        """
                    Lorem Ipsum is simply dummy text of the printing and typesetting industry.
                     Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
                        It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages,
                """.trimIndent(),
        "Solar Panels Now Capable of Generating Power from Moonlight",
        "",
        isBookmark = false,
        ""
    )
    ArticleInfoBody(
        article = article
    )
}