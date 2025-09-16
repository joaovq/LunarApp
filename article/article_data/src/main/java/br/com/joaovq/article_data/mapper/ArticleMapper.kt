package br.com.joaovq.article_data.mapper

import br.com.joaovq.article_data.local.model.ArticleEntity
import br.com.joaovq.article_data.local.view.ArticleWithBookmarkView
import br.com.joaovq.article_data.network.dto.ArticleDto
import br.com.joaovq.article_data.network.dto.AuthorDto
import br.com.joaovq.article_data.network.dto.SocialsDto
import br.com.joaovq.article_domain.model.Article
import br.com.joaovq.article_domain.model.Author
import br.com.joaovq.article_domain.model.Event
import br.com.joaovq.article_domain.model.Launch
import br.com.joaovq.article_domain.model.Socials

fun ArticleDto.toArticle(): Article {
    return Article(
        authors.map(AuthorDto::toAuthor),
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

fun AuthorDto.toAuthor() = Author(
    name = name,
    socials = socials?.toSocials()
)

fun SocialsDto.toSocials() = Socials(
    bluesky = bluesky,
    instagram = instagram,
    linkedin = linkedin,
    mastodon = mastodon,
    x = x,
    youtube = youtube
)

fun ArticleDto.toEntity(): ArticleEntity {
    return ArticleEntity(
        id = id,
        featured = featured,
        imageUrl = imageUrl,
        newsSite = newsSite,
        publishedAt = publishedAt,
        summary = summary,
        title = title,
        updatedAt = updatedAt,
        url = url,
        authors = authors
    )
}

fun ArticleWithBookmarkView.toArticle(): Article {
    return Article(
        authors = authors.map(AuthorDto::toAuthor),
        events = emptyList(),
        featured = featured,
        id = id,
        imageUrl = imageUrl,
        launches = emptyList(),
        newsSite = newsSite,
        publishedAt = publishedAt,
        summary = summary,
        title = title,
        updatedAt = updatedAt,
        isBookmark = isBookmark,
        url = url
    )
}