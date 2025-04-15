package br.com.joaovq.lunarappcompose.data.network.service

import br.com.joaovq.lunarappcompose.data.articles.network.dto.ArticleDto
import br.com.joaovq.lunarappcompose.data.articles.network.dto.ArticlesDtoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpaceFlightNewsApi {
    @GET("articles")
    suspend fun getArticles(
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
        @Query("search") query: String? = null
    ): Response<ArticlesDtoResponse>
    @GET("articles/{id}")
    suspend fun getArticleById(@Path("id") id: Int): Response<ArticleDto>
}