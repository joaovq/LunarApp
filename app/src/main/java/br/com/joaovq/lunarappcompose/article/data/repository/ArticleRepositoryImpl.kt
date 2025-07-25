package br.com.joaovq.lunarappcompose.article.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import br.com.joaovq.lunarappcompose.article.data.local.view.ArticleWithBookmarkView
import br.com.joaovq.lunarappcompose.article.data.network.paging.ArticlesRemoteMediator
import br.com.joaovq.lunarappcompose.article.domain.mapper.toArticle
import br.com.joaovq.lunarappcompose.article.domain.model.Article
import br.com.joaovq.lunarappcompose.article.domain.repository.ArticleRepository
import br.com.joaovq.lunarappcompose.bookmark.data.model.ArticleBookmarkEntity
import br.com.joaovq.lunarappcompose.data.local.LunarDatabase
import br.com.joaovq.lunarappcompose.data.network.datasource.SpaceFlightRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val remoteDataSource: SpaceFlightRemoteDataSource,
    private val localDataSource: LunarDatabase
) : ArticleRepository {
    private val articleDao = localDataSource.articleDao()
    private val articleBookmarkDao = localDataSource.bookmarkDao()

    @OptIn(ExperimentalPagingApi::class)
    override fun getArticles(limit: Int, offset: Int, query: String?): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(limit),
            remoteMediator = ArticlesRemoteMediator(
                query = query,
                database = localDataSource,
                remoteDataSource = remoteDataSource
            )
        ) {
            articleDao.pagingSource(query.orEmpty())
        }.flow.map { data -> data.map(ArticleWithBookmarkView::toArticle) }
    }

    override suspend fun getArticleById(id: Int): Result<Article?> {
        return remoteDataSource.getArticleById(id).map { it?.toArticle() }
    }

    override fun getBookmarkedArticles(): Flow<List<ArticleWithBookmarkView>> =
        articleDao.getBookmarkedArticles()

    override suspend fun saveNewBookmark(id: Int): Result<Boolean> {
        return try {
            articleBookmarkDao.insert(ArticleBookmarkEntity(articleId = id))
            Result.success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun removeBookmarkById(articleId: Int): Result<Boolean> {
        return try {
            articleBookmarkDao.removeBookmarkById(articleId)
            Result.success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}