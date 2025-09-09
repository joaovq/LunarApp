package br.com.joaovq.common.state

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object GlobalFilterStateHolder {
    private val _newsSites = MutableStateFlow(emptyList<String>())
    val filteredNewsSites: StateFlow<List<String>> get() = _newsSites.asStateFlow()

    fun setFilteredNewsSites(newsSites: List<String>) {
        _newsSites.update { newsSites }
    }
}