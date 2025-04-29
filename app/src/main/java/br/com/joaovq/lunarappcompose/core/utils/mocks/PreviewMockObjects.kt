package br.com.joaovq.lunarappcompose.core.utils.mocks

import br.com.joaovq.lunarappcompose.domain.articles.model.Article

object PreviewMockObjects {
    val article = Article(
        authors = emptyList(),
        events = emptyList(),
        featured = false,
        id = 0,
        imageUrl = "",
        newsSite = "Space News",
        publishedAt = "",
        summary = "summary test",
        updatedAt = "",
        title = "Article test title",
        url = "",
        isBookmark = false,
        launches = emptyList()
    )
}