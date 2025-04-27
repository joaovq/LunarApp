package br.com.joaovq.lunarappcompose.data.articles.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.joaovq.lunarappcompose.data.articles.model.ArticleEntity

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<ArticleEntity>)

    @Query("SELECT * FROM articles_tb WHERE title LIKE '%' || :query || '%'")
    fun pagingSource(query: String): PagingSource<Int, ArticleEntity>

    @Query("DELETE FROM articles_tb")
    suspend fun clearAll()

    @Query("DELETE FROM articles_tb WHERE title LIKE '%' || :query || '%'")
    suspend fun deleteByQuery(query: String)

}