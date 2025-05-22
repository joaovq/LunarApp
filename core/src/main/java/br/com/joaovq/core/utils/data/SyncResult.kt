package br.com.joaovq.core.utils.data

sealed class SyncResult {
    object Success : SyncResult()
    data class Error(val exception: Exception, val isRetryable: Boolean) : SyncResult()
}