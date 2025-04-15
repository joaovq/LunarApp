package br.com.joaovq.lunarappcompose.domain.mapper

import br.com.joaovq.lunarappcompose.data.network.dto.ArticleDto
import br.com.joaovq.lunarappcompose.domain.model.Article

fun ArticleDto.toArticle(): Article {
    return Article(
        authors,
        events,
        featured,
        id,
        imageUrl,
        launches,
        newsSite,
        publishedAt,
        summary,
        title,
        updatedAt,
        url
    )
}