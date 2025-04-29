package br.com.joaovq.lunarappcompose.data.bookmark.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

const val BOOKMARK_TABLE_NAME = "article_bookmark_tb"
const val ID_COLUMN_NAME = "id"
const val ARTICLE_ID_COLUMN_NAME = "article_id"

@Entity(
    tableName = BOOKMARK_TABLE_NAME,
    indices = [Index(unique = true, value = [ARTICLE_ID_COLUMN_NAME])]
)
data class ArticleBookmarkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = ARTICLE_ID_COLUMN_NAME)
    val articleId: Int
)