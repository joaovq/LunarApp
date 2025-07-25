package br.com.joaovq.lunarappcompose.article.domain.mapper

import br.com.joaovq.lunarappcompose.article.data.local.model.ArticleEntity
import br.com.joaovq.lunarappcompose.article.data.local.view.ArticleWithBookmarkView
import br.com.joaovq.lunarappcompose.article.data.network.dto.ArticleDto
import br.com.joaovq.lunarappcompose.article.domain.model.Article

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
        false,
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

fun ArticleWithBookmarkView.toArticle(): Article {
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
        isBookmark,
        url
    )
}