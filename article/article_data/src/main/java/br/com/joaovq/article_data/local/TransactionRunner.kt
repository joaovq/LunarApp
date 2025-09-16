package br.com.joaovq.article_data.local

fun interface TransactionRunner {
    suspend fun runTransaction(block: suspend () -> Unit)
}