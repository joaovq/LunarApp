package br.com.joaovq.lunarappcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.joaovq.lunarappcompose.article.data.local.dao.ArticleBookmarkDao
import br.com.joaovq.lunarappcompose.article.data.local.dao.ArticleDao
import br.com.joaovq.lunarappcompose.article.data.local.dao.RemoteKeyDao
import br.com.joaovq.lunarappcompose.article.data.local.model.ArticleEntity
import br.com.joaovq.lunarappcompose.article.data.local.model.RemoteKeys
import br.com.joaovq.lunarappcompose.article.data.local.view.ArticleWithBookmarkView
import br.com.joaovq.lunarappcompose.bookmark.data.model.ArticleBookmarkEntity

@Database(
    entities = [
        ArticleEntity::class,
        RemoteKeys::class,
        ArticleBookmarkEntity::class
    ],
    views = [ArticleWithBookmarkView::class],
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