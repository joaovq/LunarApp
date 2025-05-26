package br.com.joaovq.lunarappcompose.presentation.articles.screen

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onParent
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.runner.AndroidJUnit4
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import br.com.joaovq.article_domain.model.Article
import br.com.joaovq.article_presentation.article_list.screen.ArticlesScreen
import br.com.joaovq.lunarappcompose.MainActivity
import com.github.takahirom.roborazzi.captureRoboImage
import kotlinx.coroutines.flow.flow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
// Enable Robolectric Native Graphics (RNG)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class ArticlesScreenTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "android:Theme.Material.Light.NoActionBar"
        // ...see docs for more options
    )

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun launchArticlesScreen() {
        composeRule.setContent {
            ArticlesScreen(
                articles = flow<PagingData<Article>> { emit(PagingData.empty()) }
                    .collectAsLazyPagingItems(),
            )
        }
        composeRule.onNodeWithTag("ArticlesScreen")
            .onParent()
            .captureRoboImage("build/articles_screen.png")
    }
}