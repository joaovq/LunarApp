package br.com.joaovq.lunarappcompose.domain.articles.mapper

import br.com.joaovq.lunarappcompose.data.articles.model.ArticleEntity
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

fun ArticleDto.toEntity(): ArticleEntity {
    return ArticleEntity(
        id,
        featured,
        imageUrl,
        newsSite,
        publishedAt,
        summary,
        title,
        updatedAt,
        url
    )
}

fun ArticleEntity.toArticle(): Article {
    return Article(
        emptyList(),
        emptyList(),
        featured,
        id,
        imageUrl,
        emptyList(),
        newsSite,
        publishedAt,
        summary,
        title,
        updatedAt,
        url
    )
}