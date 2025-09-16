package br.com.joaovq.article_domain.model


data class Article(
    val authors: List<Author>,
    val events: List<Event>,
    val featured: Boolean,
    val id: Int,
    val imageUrl: String,
    val launches: List<Launch>,
    val newsSite: String,
    val publishedAt: String,
    val summary: String,
    val title: String,
    val updatedAt: String,
    val isBookmark: Boolean,
    val url: String
)
