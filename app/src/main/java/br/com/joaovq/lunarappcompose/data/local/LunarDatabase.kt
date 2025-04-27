package br.com.joaovq.lunarappcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.joaovq.lunarappcompose.data.articles.local.ArticleDao
import br.com.joaovq.lunarappcompose.data.articles.local.RemoteKeyDao
import br.com.joaovq.lunarappcompose.data.articles.model.ArticleEntity
import br.com.joaovq.lunarappcompose.data.articles.model.RemoteKeys

@Database(
    entities = [
        ArticleEntity::class,
        RemoteKeys::class
    ],
    version = 2,
    exportSchema = true
)
abstract class LunarDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun remoteKeyDao(): RemoteKeyDao

    companion object {
        const val DATABASE_NAME = "lunar_db"
    }
}