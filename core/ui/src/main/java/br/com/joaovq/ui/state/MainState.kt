package br.com.joaovq.ui.state

import kotlinx.serialization.Serializable

@Serializable
data class MainState(
    val newsSites: List<String> = listOf(),
    val filteredSites: List<String> = listOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)