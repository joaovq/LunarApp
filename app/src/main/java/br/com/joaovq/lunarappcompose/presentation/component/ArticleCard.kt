package br.com.joaovq.lunarappcompose.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.joaovq.lunarappcompose.data.network.dto.ArticleDto

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: ArticleDto
) {
    Card(modifier = modifier.padding(16.dp)) {
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
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = article.authors.joinToString { it.name },
                    maxLines = 4,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                )
                Text(
                    text = article.summary,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewArticleCard() {
    ArticleCard(
        article = ArticleDto(
            id = 1,
            title = "",
            url = "",
            imageUrl = "",
            newsSite = "",
            summary = "",
            publishedAt = "",
            updatedAt = "",
            featured = false,
            launches = emptyList(),
            events = emptyList(),
            authors = emptyList()
        )
    )
}