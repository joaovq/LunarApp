package br.com.joaovq.lunarappcompose.data.network.service

import br.com.joaovq.lunarappcompose.data.network.dto.ArticleDto
import br.com.joaovq.lunarappcompose.data.network.dto.ArticlesDtoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SpaceFlightNewsApi {
    @GET("articles")
    suspend fun getArticles(): Response<ArticlesDtoResponse>
    @GET("articles/{id}")
    suspend fun getArticleById(@Path("id") id: Int): Response<ArticleDto>
}