package br.com.joaovq.data.local

import androidx.room.withTransaction
import br.com.joaovq.article_data.local.TransactionRunner

class RoomTransactionRunner(
    private val db: LunarDatabase
) : TransactionRunner {
    override suspend fun runTransaction(block: suspend () -> Unit) {
        return db.withTransaction { block() }
    }
}