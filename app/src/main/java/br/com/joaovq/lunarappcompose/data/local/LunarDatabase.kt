package br.com.joaovq.lunarappcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.joaovq.lunarappcompose.data.articles.local.dao.ArticleBookmarkDao
import br.com.joaovq.lunarappcompose.data.articles.local.dao.ArticleDao
import br.com.joaovq.lunarappcompose.data.articles.local.dao.RemoteKeyDao
import br.com.joaovq.lunarappcompose.data.articles.local.model.ArticleEntity
import br.com.joaovq.lunarappcompose.data.articles.local.model.RemoteKeys
import br.com.joaovq.lunarappcompose.data.articles.local.view.ArticleWithBookmark
import br.com.joaovq.lunarappcompose.data.bookmark.model.ArticleBookmarkEntity

@Database(
    entities = [
        ArticleEntity::class,
        RemoteKeys::class,
        ArticleBookmarkEntity::class
    ],
    views = [ArticleWithBookmark::class],
    version = 3,
    exportSchema = true
)
abstract class LunarDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun bookmarkDao(): ArticleBookmarkDao

    companion object {
        const val DATABASE_NAME = "lunar_db"
    }
}