package br.com.joaovq.lunarappcompose.presentation.articles.screen

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onParent
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
    val composeTestRule = createComposeRule()

    @Test
    fun launchArticles() {
        composeTestRule.setContent {
            ArticlesScreen(
                articles = flow<PagingData<Article>> { emit(PagingData.empty()) }
                    .collectAsLazyPagingItems(),
            )
        }
        composeTestRule.onNode(hasTestTag("ArticlesScreen"))
            .onParent()
            .assertIsDisplayed()
            .assert(hasClickAction() or hasScrollAction())
    }
}