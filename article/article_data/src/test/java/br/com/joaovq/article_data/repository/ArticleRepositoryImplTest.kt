package br.com.joaovq.article_data.repository

import app.cash.turbine.test
import br.com.joaovq.article_data.local.TransactionRunner
import br.com.joaovq.article_data.local.dao.ArticleBookmarkDao
import br.com.joaovq.article_data.local.dao.ArticleDao
import br.com.joaovq.article_data.local.dao.RemoteKeyDao
import br.com.joaovq.article_data.network.datasource.ArticleRemoteDataSource
import br.com.joaovq.article_domain.repository.ArticleRepository
import br.com.joaovq.bookmark_data.data.model.ArticleBookmarkEntity
import br.com.joaovq.testing.utils.Faker
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ArticleRepositoryImplTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var articleDao: ArticleDao

    @MockK
    lateinit var bookmarkDao: ArticleBookmarkDao

    @MockK
    lateinit var remoteKeyDao: RemoteKeyDao

    @MockK
    lateinit var transactionRunner: TransactionRunner

    @MockK
    lateinit var remoteDataSource: ArticleRemoteDataSource
    lateinit var articleRepository: ArticleRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        articleRepository = ArticleRepositoryImpl(
            remoteDataSource,
            articleDao,
            bookmarkDao,
            remoteKeyDao,
            transactionRunner,
            UnconfinedTestDispatcher()
        )
    }

    @Test
    fun `given bookmarked articles when getBookmarkedArticles then return empty flow`() = runTest {
        every { articleDao.getBookmarkedArticles() } returns flowOf(listOf())
        val result = articleRepository.getBookmarkedArticles()
        result.test {
            assertThat(awaitItem(), CoreMatchers.equalTo(listOf()))
            awaitComplete()
        }
        verify { articleDao.getBookmarkedArticles().apply {} }
        confirmVerified(articleDao)
    }

    @Test
    @Throws(RuntimeException::class)
    fun `given bookmarked articles when getBookmarkedArticles then return one article entity`() =
        runTest {
            // given
            every { articleDao.getBookmarkedArticles() } returns flowOf(
                Faker.articlesWithBookmark().slice(0..1)
            )
            // when
            val result = articleRepository.getBookmarkedArticles()
            result.test {
                // then
                assertThat(awaitItem().isNotEmpty(), CoreMatchers.`is`(not(false)))
                awaitComplete()
            }
            verify {
                articleDao.getBookmarkedArticles().apply {}
            }
            confirmVerified(articleDao)
        }

    @Test
    @Throws(RuntimeException::class)
    fun `given id article when saveNewBookmark then return result isSuccess`() = runTest {
        // given
        val articleBookmarkEntity = ArticleBookmarkEntity(articleId = 1)
        coJustRun { bookmarkDao.insert(any()) }
        // when
        val result = articleRepository.saveNewBookmark(1)
        assertThat(result.isSuccess, CoreMatchers.`is`(true))
        assertThat(result, CoreMatchers.any(Result::class.java))
        coVerify { bookmarkDao.insert(articleBookmarkEntity) }
        confirmVerified(bookmarkDao)
    }

    @Test
    @Throws(RuntimeException::class)
    fun `given id article when saveNewBookmark throws exception then return result isFailure`() =
        runTest {
            // given
            val articleBookmarkEntity = ArticleBookmarkEntity(articleId = 1)
            coEvery { bookmarkDao.insert(any()) } throws RuntimeException()
            // when
            val result = articleRepository.saveNewBookmark(1)
            assertThat(result, CoreMatchers.any(Result::class.java))
            assertThat(result.isFailure, CoreMatchers.`is`(true))
            assertThat(result.isSuccess, CoreMatchers.`is`(false))
            assertThat(result.getOrNull(), CoreMatchers.nullValue())
            coVerify { bookmarkDao.insert(articleBookmarkEntity) }
            confirmVerified(bookmarkDao)
        }
}