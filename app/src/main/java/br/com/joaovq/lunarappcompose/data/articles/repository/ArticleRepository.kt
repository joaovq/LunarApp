package br.com.joaovq.lunarappcompose.data.articles.repository

import androidx.paging.PagingData
import br.com.joaovq.lunarappcompose.domain.articles.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun getArticles(
        limit: Int = 50,
        offset: Int = 0,
        query: String? = null
    ): Flow<PagingData<Article>>
    suspend fun getArticleById(id: Int): Result<Article?>
}