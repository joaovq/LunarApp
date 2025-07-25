package br.com.joaovq.lunarappcompose.article.domain.repository

import androidx.paging.PagingData
import br.com.joaovq.lunarappcompose.article.data.local.view.ArticleWithBookmarkView
import br.com.joaovq.lunarappcompose.article.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun getArticles(
        limit: Int = 50,
        offset: Int = 0,
        query: String? = null
    ): Flow<PagingData<Article>>

    suspend fun getArticleById(id: Int): Result<Article?>
    fun getBookmarkedArticles(): Flow<List<ArticleWithBookmarkView>>
    suspend fun saveNewBookmark(id: Int): Result<Boolean>
    suspend fun removeBookmarkById(articleId: Int): Result<Boolean>
}