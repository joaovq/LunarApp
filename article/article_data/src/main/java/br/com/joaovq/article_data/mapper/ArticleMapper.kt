package br.com.joaovq.article_data.mapper

import br.com.joaovq.article_data.local.model.ArticleEntity
import br.com.joaovq.article_data.local.view.ArticleWithBookmarkView
import br.com.joaovq.article_data.network.dto.ArticleDto
import br.com.joaovq.article_domain.model.Article
import br.com.joaovq.article_domain.model.Author
import br.com.joaovq.article_domain.model.Event
import br.com.joaovq.article_domain.model.Launch
import br.com.joaovq.article_domain.model.Socials

fun ArticleDto.toArticle(): Article {
    return Article(
        authors.map {
            Author(
                it.name,
                it.socials?.let { socials ->
                    Socials(
                        socials.bluesky,
                        socials.instagram,
                        socials.linkedin,
                        socials.mastodon,
                        socials.x,
                        socials.youtube
                    )
                }
            )
        },
        events.map { Event(it.eventId, it.provider) },
        featured,
        id,
        imageUrl,
        launches.map { Launch(it.launchId, it.provider) },
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