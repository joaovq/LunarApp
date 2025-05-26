package br.com.joaovq.lunarappcompose.presentation.viewmodel

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import br.com.joaovq.article_data.mapper.toArticle
import br.com.joaovq.article_data.network.dto.ArticleDto
import br.com.joaovq.article_presentation.article_list.viewmodel.ArticlesViewModel
import br.com.joaovq.lunarappcompose.utils.Faker
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ArticlesViewModelTest {
    @RelaxedMockK
    private lateinit var repository: br.com.joaovq.article_domain.repository.ArticleRepository
    private lateinit var viewModel: ArticlesViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = ArticlesViewModel(repository, testDispatcher)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `given articles when getArticles then return articles`() = runTest {
        // GIVEN
        val size = 50
        val fakeArticles = Faker.articles(size).map(ArticleDto::toArticle)
        every { repository.getArticles(any(), any(), any()) } returns flowOf(
            PagingData.from(fakeArticles)
        )
        val articles = viewModel.articles
        // WHEN
        val itemsSnapshot = articles.asSnapshot {
            scrollTo(index = size)
        }
        // THEN
        assertEquals(expected = fakeArticles, actual = itemsSnapshot)
    }
}