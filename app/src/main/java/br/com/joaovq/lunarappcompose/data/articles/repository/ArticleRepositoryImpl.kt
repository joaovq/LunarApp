package br.com.joaovq.lunarappcompose.data.articles.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import br.com.joaovq.lunarappcompose.data.articles.model.ArticleEntity
import br.com.joaovq.lunarappcompose.data.articles.network.paging.ArticlesRemoteMediator
import br.com.joaovq.lunarappcompose.data.local.LunarDatabase
import br.com.joaovq.lunarappcompose.data.network.datasource.SpaceFlightRemoteDataSource
import br.com.joaovq.lunarappcompose.domain.articles.mapper.toArticle
import br.com.joaovq.lunarappcompose.domain.articles.model.Article
import br.com.joaovq.lunarappcompose.domain.articles.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val remoteDataSource: SpaceFlightRemoteDataSource,
    private val localDataSource: LunarDatabase
) : ArticleRepository {
    private val articleDao = localDataSource.articleDao()

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
        }.flow.map { data -> data.map(ArticleEntity::toArticle) }
    }

    override suspend fun getArticleById(id: Int): Result<Article?> {
        return remoteDataSource.getArticleById(id).map { it?.toArticle() }
    }
}