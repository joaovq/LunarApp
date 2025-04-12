package br.com.joaovq.lunarappcompose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import br.com.joaovq.lunarappcompose.data.network.datasource.SpaceFlightRemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    spaceFlightApiRemoteDataSource: SpaceFlightRemoteDataSource,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    val articles = spaceFlightApiRemoteDataSource
        .getArticles()
        .cachedIn(viewModelScope)
        .flowOn(dispatcher)
}