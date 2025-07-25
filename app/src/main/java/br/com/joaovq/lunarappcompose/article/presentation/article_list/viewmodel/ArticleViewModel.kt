package br.com.joaovq.lunarappcompose.article.presentation.article_list.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.joaovq.lunarappcompose.article.domain.repository.ArticleRepository
import br.com.joaovq.lunarappcompose.di.annotations.IODispatcher
import br.com.joaovq.lunarappcompose.article.domain.model.Article
import br.com.joaovq.lunarappcompose.article.presentation.article_list.nav.ArticleRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val articleRepository: ArticleRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val articleRoute = savedStateHandle.toRoute<ArticleRoute>()
    private val _article = MutableStateFlow<Article?>(null)
    val article = _article.onStart { getArticleById(articleRoute.id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    fun getArticleById(id: Int) {
        viewModelScope.launch(dispatcher) {
            _isLoading.update { true }
            val result = articleRepository.getArticleById(id)
            result.onSuccess {
                _article.value = it
                _isLoading.update { false }
            }.onFailure {
                it.printStackTrace()
                _isLoading.update { false }
            }
        }
    }
}