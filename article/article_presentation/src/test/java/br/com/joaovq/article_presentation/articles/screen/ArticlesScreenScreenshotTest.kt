package br.com.joaovq.article_presentation.articles.screen

import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.joaovq.article_domain.model.Article
import br.com.joaovq.article_presentation.article_list.screen.ArticlesScreen
import br.com.joaovq.ui.theme.LunarTheme
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import kotlinx.coroutines.flow.flow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel5)
@LooperMode(LooperMode.Mode.PAUSED)
class ArticlesScreenScreenshotTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `full content initial state in articles screen`() {
        articlesScreen.onNode(hasTestTag("ArticlesScreen")).assertExists().captureRoboImage()
    }

    @Test
    fun `capture initial state in articles screen`() {
        articlesScreen.onNode(hasContentDescription( "Menu Icon app")).assertExists().captureRoboImage()
    }

    private val articlesScreen by lazy {
        composeTestRule.setContent {
            LunarTheme {
                ArticlesScreen(
                    articles = flow<PagingData<Article>> { emit(PagingData.empty()) }
                        .collectAsLazyPagingItems(),
                )
            }
        }
        composeTestRule
    }
}