package br.com.joaovq.lunarappcompose.domain.articles.repository

import androidx.paging.PagingData
import androidx.paging.map
import br.com.joaovq.lunarappcompose.data.network.datasource.SpaceFlightRemoteDataSource
import br.com.joaovq.lunarappcompose.data.articles.network.dto.ArticleDto
import br.com.joaovq.lunarappcompose.data.articles.repository.ArticleRepository
import br.com.joaovq.lunarappcompose.domain.articles.toArticle
import br.com.joaovq.lunarappcompose.domain.articles.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val remoteDataSource: SpaceFlightRemoteDataSource
) : ArticleRepository {
    override fun getArticles(limit: Int, offset: Int, query: String?): Flow<PagingData<Article>> {
        return remoteDataSource.getArticles(limit, offset, query)
            .map { data -> data.map(ArticleDto::toArticle) }
    }

    override suspend fun getArticleById(id: Int): Result<Article?> {
        return remoteDataSource.getArticleById(id).map { it?.toArticle() }
    }
}