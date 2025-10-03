package br.com.joaovq.article_presentation.article_list.state

import br.com.joaovq.article_domain.model.Article

data class ArticleBookmarkState(
    val articles: List<Article>,
    val isLoading: Boolean,
    val error: Throwable?
) {
    companion object {
        fun empty() = ArticleBookmarkState(listOf(), false, null)
    }
}