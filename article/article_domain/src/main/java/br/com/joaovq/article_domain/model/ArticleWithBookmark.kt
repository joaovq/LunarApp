package br.com.joaovq.article_domain.model

class ArticleWithBookmark(
    val id: Int = 0,
    val featured: Boolean,
    val imageUrl: String,
    val url: String,
    val summary: String,
    val isBookmark: Boolean,
    val title: String,
    val newsSite: String,
    val updatedAt: String,
    val publishedAt: String,
)