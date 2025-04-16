package br.com.joaovq.lunarappcompose.presentation.search.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import br.com.joaovq.lunarappcompose.data.articles.repository.ArticleRepository
import br.com.joaovq.lunarappcompose.di.annotations.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val articleRepository: ArticleRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val log = Timber.tag(this::class.java.simpleName)

    private val _query = MutableStateFlow<String?>(null)
    val query = _query.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val articles = query.debounce(500.milliseconds).flatMapLatest { newQuery ->
        articleRepository
            .getArticles(query = newQuery)
            .cachedIn(viewModelScope)
            .onEach { log.d("articles fetched: $it") }
            .catch { log.d("error occurred: ${it.message}") }
            .onEmpty { log.d("articles list is empty") }
            .flowOn(dispatcher)
    }

    fun onQueryChanged(query: String) {
        viewModelScope.launch {
            log.d("new query search: $query")
            _query.update { query }
        }
    }
}