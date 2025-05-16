package br.com.joaovq.article_data.network.dto

import com.google.gson.annotations.SerializedName

data class ArticleDto(
    val authors: List<AuthorDto>,
    val events: List<EventDto>,
    val featured: Boolean,
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    val launches: List<LaunchDto>,
    @SerializedName("news_site")
    val newsSite: String,
    @SerializedName("published_at")
    val publishedAt: String,
    val summary: String,
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val url: String
)