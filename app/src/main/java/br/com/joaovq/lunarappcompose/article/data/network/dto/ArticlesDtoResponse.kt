package br.com.joaovq.lunarappcompose.article.data.network.dto

data class ArticlesDtoResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<ArticleDto>
)