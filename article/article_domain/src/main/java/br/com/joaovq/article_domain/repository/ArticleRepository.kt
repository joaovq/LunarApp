package br.com.joaovq.article_domain.repository

import androidx.paging.PagingData
import br.com.joaovq.article_domain.model.Article
import br.com.joaovq.article_domain.model.ArticleWithBookmark
import br.com.joaovq.core.utils.data.SyncResult
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun getArticles(
        limit: Int = 50,
        offset: Int = 0,
        query: String? = null
    ): Flow<PagingData<Article>>

    suspend fun getArticleById(id: Int): Result<Article?>
    suspend fun syncBookmarkArticles(): SyncResult
    fun getBookmarkedArticles(): Flow<List<ArticleWithBookmark>>
    suspend fun saveNewBookmark(id: Int): Result<Boolean>
    suspend fun removeBookmarkById(articleId: Int): Result<Boolean>
}