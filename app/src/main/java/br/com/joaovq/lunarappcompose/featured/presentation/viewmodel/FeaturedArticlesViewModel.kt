package br.com.joaovq.lunarappcompose.featured.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import br.com.joaovq.article_domain.repository.ArticleRepository
import br.com.joaovq.common.di.annotations.LunarDispatcher
import br.com.joaovq.common.di.annotations.MyDispatchers
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
class FeaturedArticlesViewModel @Inject constructor(
    private val articleRepository: ArticleRepository,
    @LunarDispatcher(MyDispatchers.IO) dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    private val log = Timber.tag(this::class.java.simpleName)

    val articles = articleRepository
        .getArticles(isFeatured = true)
        .onEach { log.d("articles fetched: $it") }
        .catch { log.d("error occurred: ${it.message}") }
        .onEmpty { log.d("articles list is empty") }
        .flowOn(dispatcher)
        .cachedIn(viewModelScope)

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