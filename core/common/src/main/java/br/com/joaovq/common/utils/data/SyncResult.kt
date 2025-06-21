package br.com.joaovq.common.utils.data

sealed class SyncResult {
    data object Success : SyncResult()
    data class Error(val exception: Exception, val isRetryable: Boolean) : SyncResult()
}