package br.com.joaovq.lunarappcompose.data.network.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.joaovq.lunarappcompose.data.articles.network.dto.ArticleDto
import br.com.joaovq.lunarappcompose.data.articles.network.paging.ArticlesPagingSource
import br.com.joaovq.lunarappcompose.data.network.service.SpaceFlightNewsApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface SpaceFlightRemoteDataSource {
    fun getArticles(
        limit: Int = 50,
        offset: Int = 0,
        query: String? = null
    ): Flow<PagingData<ArticleDto>>

    suspend fun getArticleById(id: Int): Result<ArticleDto?>
}

class SpaceFlightRemoteDataSourceImpl @Inject constructor(
    private val service: SpaceFlightNewsApi
) : SpaceFlightRemoteDataSource {
    override fun getArticles(
        limit: Int,
        offset: Int,
        query: String?
    ): Flow<PagingData<ArticleDto>> {
        return Pager(config = PagingConfig(limit)) {
            ArticlesPagingSource(query, service)
        }.flow
    }

    override suspend fun getArticleById(id: Int): Result<ArticleDto?> {
        try {
            val response = service.getArticleById(id)
            return if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Request failed with code ${response.code()}"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }
    }
}