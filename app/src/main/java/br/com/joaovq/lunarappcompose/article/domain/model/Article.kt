package br.com.joaovq.lunarappcompose.article.domain.model

import br.com.joaovq.lunarappcompose.article.data.network.dto.Author
import br.com.joaovq.lunarappcompose.article.data.network.dto.Launch


data class Article(
    val authors: List<Author>,
    val events: List<br.com.joaovq.lunarappcompose.article.data.network.dto.Event>,
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
