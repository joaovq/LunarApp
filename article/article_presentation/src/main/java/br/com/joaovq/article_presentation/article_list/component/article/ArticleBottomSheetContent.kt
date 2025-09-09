package br.com.joaovq.article_presentation.article_list.component.article

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import br.com.joaovq.article_domain.model.Article
import br.com.joaovq.common.utils.ext.toLocalDateTimeFormatted
import br.com.joaovq.ui.theme.LocalDimen

@Composable
fun ArticleBottomSheetContent(
    modifier: Modifier = Modifier,
    article: Article?,
) {
    val dimen = LocalDimen.current
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
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
        Text(
            modifier = Modifier.padding(horizontal = dimen.large),
            text = "Summary",
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
        )
        Spacer(modifier = Modifier.height(dimen.medium))
        Text(
            modifier = Modifier.padding(horizontal = dimen.large),
            text = article?.summary.orEmpty(),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(dimen.medium))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimen.large, vertical = dimen.large),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimen.large, alignment = Alignment.Start)
        ) {
            Column {
                article?.authors?.let {
                    Text(
                        "Authors",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Text(
                        text = it.joinToString(", ") { author -> author.name },
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Column {
                article?.newsSite?.let {
                    Text(
                        "Site",
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