package br.com.joaovq.lunarappcompose.data.articles.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val REMOTE_KEYS_TABLE_NAME = "remote_keys_tb"
const val QUERY_COLUMN_NAME = "remote_query"
const val PREV_KEY_COLUMN_NAME = "prev_key"
const val NEXT_KEY_COLUMN_NAME = "next_key"

@Entity(tableName = REMOTE_KEYS_TABLE_NAME)
data class RemoteKeys(
    @PrimaryKey
    @ColumnInfo(QUERY_COLUMN_NAME)
    val query: String,
    @ColumnInfo(PREV_KEY_COLUMN_NAME)
    val prevKey: Int?,
    @ColumnInfo(NEXT_KEY_COLUMN_NAME)
    val nextKey: Int?
)