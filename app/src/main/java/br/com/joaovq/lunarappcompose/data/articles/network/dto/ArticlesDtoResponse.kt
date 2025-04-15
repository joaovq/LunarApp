package br.com.joaovq.lunarappcompose.data.articles.network.dto

data class ArticlesDtoResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<ArticleDto>
)