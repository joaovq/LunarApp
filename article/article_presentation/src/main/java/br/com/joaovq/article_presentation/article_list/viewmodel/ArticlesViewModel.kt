package br.com.joaovq.article_presentation.article_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import br.com.joaovq.core.di.annotations.IODispatcher
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
    private val articleRepository: br.com.joaovq.article_domain.repository.ArticleRepository,
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
            if (!isBookmark) {
                articleRepository.removeBookmarkById(id)
            } else {
                articleRepository.saveNewBookmark(id)
            }
        }
    }
}