package br.com.joaovq.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.joaovq.article_data.local.AuthorTypeConverter
import br.com.joaovq.article_data.local.dao.ArticleBookmarkDao
import br.com.joaovq.article_data.local.dao.ArticleDao
import br.com.joaovq.article_data.local.dao.RemoteKeyDao
import br.com.joaovq.article_data.local.model.ArticleEntity
import br.com.joaovq.article_data.local.model.RemoteKeys
import br.com.joaovq.article_data.local.view.ArticleWithBookmarkView
import br.com.joaovq.bookmark_data.data.model.ArticleBookmarkEntity

@Database(
    entities = [
        ArticleEntity::class,
        RemoteKeys::class,
        ArticleBookmarkEntity::class
    ],
    views = [ArticleWithBookmarkView::class],
    version = 4,
    exportSchema = true
)
@TypeConverters(value = [AuthorTypeConverter::class])
abstract class LunarDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun bookmarkDao(): ArticleBookmarkDao

    companion object {
        const val DATABASE_NAME = "lunar_db"
    }
}