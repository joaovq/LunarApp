package br.com.joaovq.lunarappcompose.domain.articles

import br.com.joaovq.lunarappcompose.data.articles.network.dto.ArticleDto
import br.com.joaovq.lunarappcompose.domain.articles.model.Article

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