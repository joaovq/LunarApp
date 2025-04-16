package br.com.joaovq.lunarappcompose.presentation.articles.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.joaovq.lunarappcompose.data.articles.repository.ArticleRepository
import br.com.joaovq.lunarappcompose.di.annotations.IODispatcher
import br.com.joaovq.lunarappcompose.domain.articles.model.Article
import br.com.joaovq.lunarappcompose.presentation.articles.nav.ArticleRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    val article = _article.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        getArticleById(articleRoute.id)
    }

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