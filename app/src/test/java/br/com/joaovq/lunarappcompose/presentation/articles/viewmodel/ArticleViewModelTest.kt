package br.com.joaovq.lunarappcompose.presentation.articles.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.internalToRoute
import app.cash.turbine.test
import br.com.joaovq.article_domain.repository.ArticleRepository
import br.com.joaovq.article_domain.mapper.toArticle
import br.com.joaovq.lunarappcompose.article.presentation.article_list.viewmodel.ArticleViewModel
import br.com.joaovq.lunarappcompose.article.presentation.article_list.nav.ArticleRoute
import br.com.joaovq.lunarappcompose.utils.Faker
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
class ArticleViewModelTest {
    @RelaxedMockK
    private lateinit var repository: br.com.joaovq.article_domain.repository.ArticleRepository
    private lateinit var viewModel: ArticleViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mockkStatic("androidx.navigation.SavedStateHandleKt")
        val savedStateHandle = mockk<SavedStateHandle>()
        every { savedStateHandle.internalToRoute<Any>(any(), any()) } returns ArticleRoute(1)
        viewModel = ArticleViewModel(savedStateHandle, repository, testDispatcher)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `given id when getArticleById then return article`() = runTest {
        // GIVEN
        coEvery {
            repository.getArticleById(any())
        } returns Result.success(Faker.articles()[0].toArticle())
        //WHEN
        viewModel.article.test(10.seconds) {
            // THEN
            assertNull(expectMostRecentItem())
            val actual = awaitItem()
            assertNotNull(actual)
            assertEquals(actual.id, 0)
        }
    }
}