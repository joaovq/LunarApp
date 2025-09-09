package br.com.joaovq.article_data.network.service

import br.com.joaovq.article_data.network.dto.ArticleDto
import br.com.joaovq.article_data.network.dto.ArticlesDtoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleService {
    @GET("articles")
    suspend fun getArticles(
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
        @Query("search") query: String? = null,
        @Query("news_site") newsSites: String? = null,
        @Query("ordering") ordering: List<String>? = listOf("-published_at", "-updated_at"),
        @Query("is_featured") isFeatured: Boolean? = null,
    ): Response<ArticlesDtoResponse>

    @GET("articles/{id}")
    suspend fun getArticleById(@Path("id") id: Int): Response<ArticleDto>
}