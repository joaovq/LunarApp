package br.com.joaovq.lunarappcompose

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaovq.common.state.GlobalFilterStateHolder
import br.com.joaovq.data.network.datasource.SpaceFlightRemoteDataSource
import br.com.joaovq.ui.state.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val globalFilterStateHolder: GlobalFilterStateHolder,
    private val remoteDatasource: SpaceFlightRemoteDataSource
) : ViewModel() {
    private val log = Timber.tag(this::class.java.simpleName)
    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()
    private val error = MutableSharedFlow<String>()

    fun getNewsSites() {
        viewModelScope.launch {
            _mainState.update {
                it.copy(
                    isLoading = true
                )
            }
            remoteDatasource.getInfo().onSuccess { response ->
                _mainState.update {
                    it.copy(
                        newsSites = response?.newsSites ?: listOf(),
                        isLoading = false
                    )
                }
            }.onFailure { th ->
                _mainState.update {
                    it.copy(
                        newsSites = listOf(),
                        isLoading = false
                    )
                }
                error.emit(th.message ?: "Internal Error occurred")
            }
        }
    }

    fun onFilterClicked(filter: String) {
        viewModelScope.launch {
            _mainState.updateAndGet {
                it.copy(
                    filteredSites = if (it.filteredSites.contains(filter)) {
                        it.filteredSites.filter { site -> site != filter }
                    } else {
                        it.filteredSites.plus(filter)
                    }
                )
            }
        }
    }

    fun onResultClicked() {
        val filteredSites = _mainState.value.filteredSites
        globalFilterStateHolder.setFilteredNewsSites(filteredSites)
        savedStateHandle["filtered_news_sites"] = filteredSites.toTypedArray()
    }
}