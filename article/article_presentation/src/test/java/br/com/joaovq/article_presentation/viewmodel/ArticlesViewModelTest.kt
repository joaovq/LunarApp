package br.com.joaovq.article_presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import br.com.joaovq.article_domain.repository.ArticleRepository
import br.com.joaovq.article_presentation.article_list.viewmodel.ArticlesViewModel
import br.com.joaovq.article_presentation.utils.Faker
import br.com.joaovq.common.state.GlobalFilterStateHolder
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class ArticlesViewModelTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var repository: ArticleRepository

    private val globalFilterStateHolder: GlobalFilterStateHolder = GlobalFilterStateHolder()

    @MockK
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: ArticlesViewModel
    val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        viewModel = ArticlesViewModel(
            savedStateHandle = savedStateHandle,
            globalFilterStateHolder = GlobalFilterStateHolder(),
            articleRepository = repository,
            dispatcher = testDispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given articles when getArticles then return articles`() =
        runTest(testDispatcher) {
            // GIVEN
            val size = 50
            val fakeArticles = Faker.articles(size)
            globalFilterStateHolder.setFilteredNewsSites(listOf("teste"))
            every { repository.getArticles(newsSites = any()) } returns flowOf(
                PagingData.from(fakeArticles)
            )
            val articles = viewModel.articles
            // WHEN
            val itemsSnapshot = articles.asSnapshot {
                scrollTo(index = size)
            }
            advanceUntilIdle()
            // THEN
            assertEquals(expected = fakeArticles, actual = itemsSnapshot)
        }

    @Test
    fun `given isBookmark true and id 2 when onBookmarkChanged then save bookmark`() =
        runTest(testDispatcher) {
            // GIVEN
            coJustRun { repository.saveNewBookmark(any()) }
            // WHEN
            viewModel.onBookmarkChanged(true, 2)
            advanceUntilIdle()
            // THEN
            coVerify { repository.saveNewBookmark(2) }
        }

    @Test
    fun `given isBookmark false and id 2 when onBookmarkChanged then remove bookmark`() =
        runTest(testDispatcher) {
            // GIVEN
            coJustRun { repository.removeBookmarkById(any()) }
            // WHEN
            viewModel.onBookmarkChanged(false, 2)
            advanceUntilIdle()
            // THEN
            coVerify { repository.removeBookmarkById(2) }
        }
}