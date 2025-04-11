package br.com.joaovq.lunarappcompose.data.network.datasource

import android.util.Log
import br.com.joaovq.lunarappcompose.data.network.dto.ArticleDto
import br.com.joaovq.lunarappcompose.data.network.dto.ArticlesDtoResponse
import br.com.joaovq.lunarappcompose.data.network.service.SpaceFlightNewsApi
import javax.inject.Inject


interface SpaceFlightApiRemoteDataSource {
    suspend fun getArticles(): Result<ArticlesDtoResponse?>
    suspend fun getArticleById(id: Int): Result<ArticleDto?>
}

class SpaceFlightApiRemoteDataSourceImpl @Inject constructor(
    private val service: SpaceFlightNewsApi
) : SpaceFlightApiRemoteDataSource {
    override suspend fun getArticles(): Result<ArticlesDtoResponse?> {
        try {
            val response = service.getArticles()
            return if (response.isSuccessful) {
                Log.d("SpaceFlightApiRemoteDataSourceImpl", "getArticles: ${response.body()}")
                Result.success(response.body())
            } else {
                Result.failure(Exception("Request failed with code ${response.code()}"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
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