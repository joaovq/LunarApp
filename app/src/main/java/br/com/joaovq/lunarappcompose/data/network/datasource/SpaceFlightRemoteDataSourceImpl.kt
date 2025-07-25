package br.com.joaovq.lunarappcompose.data.network.datasource

import br.com.joaovq.lunarappcompose.article.data.network.dto.ArticleDto
import br.com.joaovq.lunarappcompose.article.data.network.dto.ArticlesDtoResponse
import br.com.joaovq.lunarappcompose.data.network.service.SpaceFlightNewsApi
import javax.inject.Inject


interface SpaceFlightRemoteDataSource {
    suspend fun getArticles(
        limit: Int = 50,
        offset: Int = 0,
        query: String? = null
    ): ArticlesDtoResponse?

    suspend fun getArticleById(id: Int): Result<ArticleDto?>
}

class SpaceFlightRemoteDataSourceImpl @Inject constructor(
    private val service: SpaceFlightNewsApi
) : SpaceFlightRemoteDataSource {
    override suspend fun getArticles(
        limit: Int,
        offset: Int,
        query: String?
    ): ArticlesDtoResponse? {
        try {
            val response = service.getArticles(limit, offset, query)
            return if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
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