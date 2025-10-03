package br.com.joaovq.bookmark_presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaovq.article_domain.model.Article
import br.com.joaovq.article_domain.repository.ArticleRepository
import br.com.joaovq.article_presentation.article_list.state.ArticleBookmarkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ArticlesBookmarkViewModel @Inject constructor(
    private val articlesRepository: ArticleRepository
) : ViewModel() {
    private val log = Timber.tag(this::class.java.simpleName)
    private val _state = MutableStateFlow(ArticleBookmarkState.empty())
    val state = articlesRepository.getBookmarkedArticles().combine(_state) { bookmarks, state ->
        state.copy(articles = bookmarks.map {
            Article(
                authors = emptyList(),
                featured = it.featured,
                imageUrl = it.imageUrl,
                newsSite = it.newsSite,
                publishedAt = it.publishedAt,
                summary = it.summary,
                title = it.title,
                updatedAt = it.updatedAt,
                url = it.url,
                isBookmark = it.isBookmark,
                id = it.id,
                launches = emptyList(),
                events = emptyList()
            )
        })
    }.onStart {
        //if (it.articles.isEmpty()) {
        log.d("sync bookmark articles")
        _state.update { state -> state.copy(isLoading = true) }
        articlesRepository.syncBookmarkArticles()
        log.d("finish sync bookmark articles")
        _state.update { state -> state.copy(isLoading = false) }
        // }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        ArticleBookmarkState.empty()
    )
}