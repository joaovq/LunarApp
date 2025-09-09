package br.com.joaovq.article_presentation.article_list.component.article

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.joaovq.article_domain.model.Article
import br.com.joaovq.article_presentation.R
import br.com.joaovq.common.utils.ext.toLocalDateTimeFormatted

@Composable
fun ArticleBottomSheetContent(
    modifier: Modifier = Modifier,
    article: Article?
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    modifier = Modifier.wrapContentHeight(Alignment.CenterVertically),
                    text = buildAnnotatedString {
                        article?.url?.let {
                            withLink(LinkAnnotation.Url(it)) {
                                append(stringResource(R.string.open_in_browser))
                                append(" ")
                                appendInlineContent("open-in-new-icon")
                            }
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
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = article?.summary.orEmpty(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}