package br.com.joaovq.lunarappcompose.presentation.bookmark.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaovq.lunarappcompose.data.articles.local.view.ArticleWithBookmark
import br.com.joaovq.lunarappcompose.domain.articles.mapper.toArticle
import br.com.joaovq.lunarappcompose.domain.articles.repository.ArticleRepository
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
        .map { bookmarks -> bookmarks.map(ArticleWithBookmark::toArticle) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

}