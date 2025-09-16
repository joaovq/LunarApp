package br.com.joaovq.article_data.network.dto

data class ArticlesDtoResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<ArticleDto>
)