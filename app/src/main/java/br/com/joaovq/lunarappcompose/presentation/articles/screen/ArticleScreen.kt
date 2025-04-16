@file:OptIn(ExperimentalMaterial3Api::class)

package br.com.joaovq.lunarappcompose.presentation.articles.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.joaovq.lunarappcompose.R
import br.com.joaovq.lunarappcompose.core.utils.ext.toLocalDateTimeFormatted
import br.com.joaovq.lunarappcompose.domain.articles.model.Article
import br.com.joaovq.lunarappcompose.ui.theme.LunarTheme
import coil3.compose.rememberAsyncImagePainter
import java.time.OffsetDateTime

@Composable
fun ArticleScreen(
    modifier: Modifier = Modifier,
    article: Article,
    isLoading: Boolean = false,
    onNavigateUp: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
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
                    IconButton(onClick = {

                    }) {
                        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
                    }
                },
                title = {}
            )
        }
    ) { innerPadding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
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
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    text = buildAnnotatedString {
                        article.publishedAt.toLocalDateTimeFormatted()
                            ?.let { publishedAtFormatted ->
                                append(publishedAtFormatted)
                            }
                    },
                    maxLines = 1,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )
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
                ""
            )
        )
    }
}