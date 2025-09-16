package br.com.joaovq.testing.utils

import br.com.joaovq.article_data.local.view.ArticleWithBookmarkView
import br.com.joaovq.article_data.network.dto.ArticleDto

object Faker {
    fun articles(size: Int = 50) = List(size) {
        article(id = it)
    }

    fun article(id: Int = 1, title: String = "Title $id", summary: String = "Summary $id") =
        ArticleDto(
            id = id,
            title = title,
            url = "",
            imageUrl = "",
            newsSite = "",
            summary = summary,
            publishedAt = "",
            updatedAt = "",
            featured = false,
            launches = emptyList(),
            events = emptyList(),
            authors = emptyList()
        )
    fun articlesWithBookmark(): List<ArticleWithBookmarkView> {
        return List(50) {
            ArticleWithBookmarkView(
                id = it,
                title = "Title $it",
                url = "",
                imageUrl = "",
                newsSite = "",
                summary = "Summary $it",
                publishedAt = "",
                updatedAt = "",
                featured = false,
                isBookmark = true,
                authors = emptyList()
            )
        }
    }
}