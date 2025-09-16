package br.com.joaovq.article_presentation.utils

import br.com.joaovq.article_domain.model.Article
import kotlin.random.Random

object Faker {
    fun articles(size: Int = 50) = List(size) {
        article(id = it)
    }

    fun article(id: Int = 1, title: String = "Title $id", summary: String = "Summary $id") =
        Article(
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
            authors = emptyList(),
            isBookmark = Random(100).nextBoolean()
        )
}