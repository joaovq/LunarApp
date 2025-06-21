package br.com.joaovq.data.network.datasource

import br.com.joaovq.article_data.network.dto.ArticleDto
import br.com.joaovq.article_data.network.dto.ArticlesDtoResponse
import br.com.joaovq.common.di.annotations.LunarDispatcher
import br.com.joaovq.common.di.annotations.MyDispatchers
import br.com.joaovq.data.network.dto.InfoDto
import br.com.joaovq.data.network.service.SpaceFlightNewsApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject


interface SpaceFlightRemoteDataSource {
    suspend fun getArticles(
        limit: Int = 50,
        offset: Int = 0,
        query: String? = null
    ): ArticlesDtoResponse?

    suspend fun getArticleById(id: Int): Result<ArticleDto?>
    suspend fun getInfo(): Result<InfoDto?>
}

class SpaceFlightRemoteDataSourceImpl @Inject constructor(
    private val service: SpaceFlightNewsApi,
    @LunarDispatcher(MyDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
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

    override suspend fun getInfo(): Result<InfoDto?> = withContext(ioDispatcher) {
        try {
            val response = service.getInfo()
            return@withContext if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext Result.failure(e)
        }
    }
}