package br.com.joaovq.lunarappcompose.bookmark.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaovq.lunarappcompose.article.data.local.view.ArticleWithBookmarkView
import br.com.joaovq.article_domain.mapper.toArticle
import br.com.joaovq.article_domain.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ArticlesBookmarkViewModel @Inject constructor(
    articlesRepository: br.com.joaovq.article_domain.repository.ArticleRepository
) : ViewModel() {
    val bookmarks = articlesRepository.getBookmarkedArticles()
        .map { bookmarks -> bookmarks.map(ArticleWithBookmarkView::toArticle) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

}