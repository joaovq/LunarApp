package br.com.joaovq.lunarappcompose.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaovq.lunarappcompose.data.network.datasource.SpaceFlightApiRemoteDataSource
import br.com.joaovq.lunarappcompose.data.network.dto.ArticlesDtoResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val spaceFlightApiRemoteDataSource: SpaceFlightApiRemoteDataSource
) : ViewModel() {
    private val _articles = MutableStateFlow<ArticlesDtoResponse?>(null)
    val articles = _articles.asStateFlow()

    init {
        getArticles()
    }

    fun getArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            spaceFlightApiRemoteDataSource.getArticles().onSuccess { articles ->
                Log.d("ArticlesViewModel", "getArticles: $articles")
                _articles.update { articles }
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

}