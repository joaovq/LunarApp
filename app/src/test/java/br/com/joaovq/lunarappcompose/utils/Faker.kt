package br.com.joaovq.lunarappcompose.utils

import br.com.joaovq.lunarappcompose.data.network.dto.ArticleDto

object Faker {
    fun articles(size: Int = 50) = List(size) {
        article(id = it)
    }

    fun article(id: Int = 1, title: String = "Title $id", summary: String = "Summary $id") =
        ArticleDto(
            id = 1,
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
}