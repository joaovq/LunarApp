package br.com.joaovq.lunarappcompose.domain.articles.model

import br.com.joaovq.lunarappcompose.data.articles.network.dto.Author
import br.com.joaovq.lunarappcompose.data.articles.network.dto.Event
import br.com.joaovq.lunarappcompose.data.articles.network.dto.Launch

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
    val url: String
)
