package br.com.joaovq.lunarappcompose.data.articles.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.joaovq.lunarappcompose.data.bookmark.model.ARTICLE_ID_COLUMN_NAME
import br.com.joaovq.lunarappcompose.data.bookmark.model.ArticleBookmarkEntity
import br.com.joaovq.lunarappcompose.data.bookmark.model.BOOKMARK_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleBookmarkDao {
    @Insert
    suspend fun insert(bookmark: ArticleBookmarkEntity)

    @Query("SELECT * FROM $BOOKMARK_TABLE_NAME")
    fun getBookmarks(): Flow<ArticleBookmarkEntity>

    @Query("DELETE FROM $BOOKMARK_TABLE_NAME")
    suspend fun clearAll()

    @Query("DELETE FROM $BOOKMARK_TABLE_NAME WHERE $ARTICLE_ID_COLUMN_NAME=:id")
    suspend fun removeBookmarkById(id: Int)
}