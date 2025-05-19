package br.com.joaovq.article_data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.joaovq.article_data.local.model.ARTICLES_TABLE_NAME
import br.com.joaovq.article_data.local.model.ArticleEntity
import br.com.joaovq.article_data.local.view.ArticleWithBookmarkView
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<ArticleEntity>)

    @Query("SELECT * FROM ArticleWithBookmarkView WHERE title LIKE '%' || :query || '%'")
    fun pagingSource(query: String): PagingSource<Int, ArticleWithBookmarkView>

    @Query("SELECT * FROM ArticleWithBookmarkView WHERE isBookmark = 1")
    fun getBookmarkedArticles(): Flow<List<ArticleWithBookmarkView>>

    @Query("DELETE FROM $ARTICLES_TABLE_NAME")
    suspend fun clearAll()

    @Query("DELETE FROM $ARTICLES_TABLE_NAME WHERE title LIKE '%' || :query || '%'")
    suspend fun deleteByQuery(query: String)

}