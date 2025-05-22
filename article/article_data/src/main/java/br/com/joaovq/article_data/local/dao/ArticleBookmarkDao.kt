package br.com.joaovq.article_data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.joaovq.bookmark_data.data.model.ARTICLE_ID_COLUMN_NAME
import br.com.joaovq.bookmark_data.data.model.ArticleBookmarkEntity
import br.com.joaovq.bookmark_data.data.model.BOOKMARK_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleBookmarkDao {
    @Insert
    suspend fun insert(bookmark: ArticleBookmarkEntity)

    @Query("SELECT * FROM $BOOKMARK_TABLE_NAME")
    fun getBookmarksFlow(): Flow<List<ArticleBookmarkEntity>>

    @Query("SELECT * FROM $BOOKMARK_TABLE_NAME")
    suspend fun getBookmarks(): List<ArticleBookmarkEntity>

    @Query("DELETE FROM $BOOKMARK_TABLE_NAME")
    suspend fun clearAll()

    @Query("DELETE FROM $BOOKMARK_TABLE_NAME WHERE $ARTICLE_ID_COLUMN_NAME=:id")
    suspend fun removeBookmarkById(id: Int)
}