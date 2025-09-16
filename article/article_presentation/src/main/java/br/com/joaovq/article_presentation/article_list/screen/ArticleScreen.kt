package br.com.joaovq.article_presentation.article_list.screen

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.joaovq.article_domain.model.Article
import br.com.joaovq.article_presentation.article_list.component.article.ArticleInfoBody
import br.com.joaovq.article_presentation.article_list.component.article.ArticlePreviewParameterProvider
import br.com.joaovq.article_presentation.article_list.component.article.ArticleTopAppBar
import br.com.joaovq.article_presentation.article_list.component.article.ArticleTopAppBarActions
import br.com.joaovq.article_presentation.article_list.viewmodel.ArticleViewModel
import br.com.joaovq.ui.theme.LocalDimen
import br.com.joaovq.ui.theme.LunarTheme


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
    val context = LocalContext.current
    Scaffold(
        modifier = modifier,
        topBar = {
            ArticleTopAppBar(
                onNavigateUp = onNavigateUp,
                article = article,
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

        },
    ) { innerPadding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .then(Modifier.padding(top = dimen.large)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            ArticleInfoBody(
                modifier = Modifier.padding(innerPadding),
                article = article
            )
        }
    }
}

@Preview(name = "Article screen preview light theme")
@Composable
private fun PreviewArticleScreen(
    @PreviewParameter(ArticlePreviewParameterProvider::class) article: Article,
) {
    LunarTheme {
        ArticleScreen(article = article)
    }
}