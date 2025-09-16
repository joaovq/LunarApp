package br.com.joaovq.bookmark_presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaovq.article_domain.model.Article
import br.com.joaovq.article_domain.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ArticlesBookmarkViewModel @Inject constructor(
    articlesRepository: ArticleRepository
) : ViewModel() {
    val bookmarks = articlesRepository.getBookmarkedArticles()
        .map { bookmarks ->
            bookmarks.map {
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
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

}