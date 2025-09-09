package br.com.joaovq.article_presentation.article_list.mocks

import br.com.joaovq.article_domain.model.Article

object PreviewMockObjects {
    val article = Article(
        authors = emptyList(),
        events = emptyList(),
        featured = false,
        id = 0,
        imageUrl = "",
        newsSite = "Space News",
        publishedAt = "2025-09-20 18:23:00",
        summary = "summary test",
        updatedAt = "",
        title = "Article test title",
        url = "",
        isBookmark = false,
        launches = emptyList()
    )
}