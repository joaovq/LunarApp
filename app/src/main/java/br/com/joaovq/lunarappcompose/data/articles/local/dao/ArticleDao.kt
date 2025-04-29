package br.com.joaovq.lunarappcompose.data.articles.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.joaovq.lunarappcompose.data.articles.local.model.ARTICLES_TABLE_NAME
import br.com.joaovq.lunarappcompose.data.articles.local.model.ArticleEntity
import br.com.joaovq.lunarappcompose.data.articles.local.view.ArticleWithBookmark
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<ArticleEntity>)

    @Query("SELECT * FROM ArticleWithBookmark WHERE title LIKE '%' || :query || '%'")
    fun pagingSource(query: String): PagingSource<Int, ArticleWithBookmark>

    @Query("SELECT * FROM ArticleWithBookmark WHERE isBookmark = 1")
    fun getBookmarkedArticles(): Flow<List<ArticleWithBookmark>>

    @Query("DELETE FROM $ARTICLES_TABLE_NAME")
    suspend fun clearAll()

    @Query("DELETE FROM $ARTICLES_TABLE_NAME WHERE title LIKE '%' || :query || '%'")
    suspend fun deleteByQuery(query: String)

}