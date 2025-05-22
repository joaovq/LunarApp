package br.com.joaovq.article_data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import br.com.joaovq.article_data.local.TransactionRunner
import br.com.joaovq.article_data.local.dao.ArticleBookmarkDao
import br.com.joaovq.article_data.local.dao.ArticleDao
import br.com.joaovq.article_data.local.dao.RemoteKeyDao
import br.com.joaovq.article_data.local.view.ArticleWithBookmarkView
import br.com.joaovq.article_data.mapper.toArticle
import br.com.joaovq.article_data.mapper.toEntity
import br.com.joaovq.article_data.network.datasource.ArticleRemoteDataSource
import br.com.joaovq.article_data.network.dto.ArticleDto
import br.com.joaovq.article_data.network.paging.ArticlesRemoteMediator
import br.com.joaovq.article_domain.model.Article
import br.com.joaovq.article_domain.model.ArticleWithBookmark
import br.com.joaovq.article_domain.repository.ArticleRepository
import br.com.joaovq.bookmark_data.data.model.ArticleBookmarkEntity
import br.com.joaovq.core.di.annotations.IODispatcher
import br.com.joaovq.core.utils.data.SyncResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val remoteDataSource: ArticleRemoteDataSource,
    private val articleDao: ArticleDao,
    private val bookmarkDao: ArticleBookmarkDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val transactionRunner: TransactionRunner,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : ArticleRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getArticles(limit: Int, offset: Int, query: String?): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(limit),
            remoteMediator = ArticlesRemoteMediator(
                query = query,
                articleDao = articleDao,
                remoteKeyDao = remoteKeyDao,
                remoteDataSource = remoteDataSource,
                transactionRunner = transactionRunner
            )
        ) {
            articleDao.pagingSource(query.orEmpty())
        }.flow.map { data -> data.map(ArticleWithBookmarkView::toArticle) }
    }

    override suspend fun getArticleById(id: Int): Result<Article?> {
        return remoteDataSource.getArticleById(id).map { it?.toArticle() }
    }

    override suspend fun syncBookmarkArticles(): SyncResult = withContext(ioDispatcher) {
        try {
            val bookmarks = bookmarkDao.getBookmarks()
            val bookmarkDeferreds = mutableListOf<Deferred<Result<ArticleDto?>>>()
            bookmarks.forEach { bookmark ->
                bookmarkDeferreds.add(
                    async {
                        val result = remoteDataSource.getArticleById(bookmark.articleId)
                        result.onSuccess {
                            it?.toEntity()?.let { safeArticle ->
                                articleDao.insertAll(listOf(safeArticle))
                            }
                        }
                    }
                )
            }
            bookmarkDeferreds.awaitAll()
            return@withContext SyncResult.Success
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext SyncResult.Error(e, false)
        }
    }

    override fun getBookmarkedArticles(): Flow<List<ArticleWithBookmark>> =
        articleDao.getBookmarkedArticles().map {
            it.map { article ->
                ArticleWithBookmark(
                    id = article.id,
                    featured = article.featured,
                    imageUrl = article.imageUrl,
                    url = article.url,
                    summary = article.summary,
                    isBookmark = article.isBookmark,
                    title = article.title,
                    newsSite = article.newsSite,
                    updatedAt = article.updatedAt,
                    publishedAt = article.publishedAt
                )
            }
        }

    override suspend fun saveNewBookmark(id: Int): Result<Boolean> {
        return try {
            bookmarkDao.insert(ArticleBookmarkEntity(articleId = id))
            Result.success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun removeBookmarkById(articleId: Int): Result<Boolean> {
        return try {
            bookmarkDao.removeBookmarkById(articleId)
            Result.success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}