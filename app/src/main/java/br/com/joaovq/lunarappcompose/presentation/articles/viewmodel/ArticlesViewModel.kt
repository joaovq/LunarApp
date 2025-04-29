package br.com.joaovq.lunarappcompose.presentation.articles.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import br.com.joaovq.lunarappcompose.di.annotations.IODispatcher
import br.com.joaovq.lunarappcompose.domain.articles.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val articleRepository: ArticleRepository,
    @IODispatcher dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val log = Timber.tag(this::class.java.simpleName)
    val articles = articleRepository
        .getArticles()
        .cachedIn(viewModelScope)
        .onEach { log.d("articles fetched: $it") }
        .catch { log.d("error occurred: ${it.message}") }
        .onEmpty { log.d("articles list is empty") }
        .flowOn(dispatcher)


    fun onBookmarkChanged(isBookmark: Boolean, id: Int) {
        viewModelScope.launch {
            if (isBookmark) {
                articleRepository.removeBookmarkById(id)
            } else {
                articleRepository.saveNewBookmark(id)
            }
        }
    }
}