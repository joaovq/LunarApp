package br.com.joaovq.lunarappcompose.presentation.articles.screen

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.joaovq.article_domain.model.Article
import br.com.joaovq.article_presentation.article_list.screen.ArticlesScreen
import br.com.joaovq.lunarappcompose.MainActivity
import kotlinx.coroutines.flow.flow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticlesScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun launchArticles() {
        composeTestRule.setContent {
            ArticlesScreen(
                articles = flow<PagingData<Article>> { emit(PagingData.empty()) }
                    .collectAsLazyPagingItems(),
            )
        }
    }
}