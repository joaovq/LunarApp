package br.com.joaovq.lunarappcompose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import br.com.joaovq.lunarappcompose.data.network.datasource.SpaceFlightRemoteDataSource
import br.com.joaovq.lunarappcompose.di.annotations.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    spaceFlightApiRemoteDataSource: SpaceFlightRemoteDataSource,
    @IODispatcher dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val log = Timber.tag(this::class.java.simpleName)
    val articles = spaceFlightApiRemoteDataSource
        .getArticles()
        .cachedIn(viewModelScope)
        .onEach { log.d("articles fetched: $it") }
        .catch { log.d("error occurred: ${it.message}") }
        .onEmpty { log.d("articles list is empty") }
        .flowOn(dispatcher)
}