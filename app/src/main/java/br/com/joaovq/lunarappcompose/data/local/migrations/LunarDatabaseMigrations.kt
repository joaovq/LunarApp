package br.com.joaovq.lunarappcompose.data.local.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.joaovq.lunarappcompose.data.articles.local.model.NEXT_KEY_COLUMN_NAME
import br.com.joaovq.lunarappcompose.data.articles.local.model.PREV_KEY_COLUMN_NAME
import br.com.joaovq.lunarappcompose.data.articles.local.model.QUERY_COLUMN_NAME
import br.com.joaovq.lunarappcompose.data.articles.local.model.REMOTE_KEYS_TABLE_NAME
import br.com.joaovq.lunarappcompose.data.bookmark.model.ARTICLE_ID_COLUMN_NAME
import br.com.joaovq.lunarappcompose.data.bookmark.model.BOOKMARK_TABLE_NAME
import br.com.joaovq.lunarappcompose.data.bookmark.model.ID_COLUMN_NAME

object LunarDatabaseMigrations {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
            CREATE TABLE IF NOT EXISTS `$REMOTE_KEYS_TABLE_NAME` (
                `$QUERY_COLUMN_NAME` TEXT PRIMARY KEY NOT NULL,
                `$PREV_KEY_COLUMN_NAME` INTEGER NULL,
                `$NEXT_KEY_COLUMN_NAME` INTEGER NULL
            )
        """.trimIndent()
            )
        }
    }
    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
            CREATE TABLE IF NOT EXISTS `$BOOKMARK_TABLE_NAME` (
                `$ID_COLUMN_NAME` INTEGER PRIMARY KEY NOT NULL,
                `$ARTICLE_ID_COLUMN_NAME` INTEGER UNIQUE NOT NULL
            )
        """.trimIndent()
            )
        }
    }
}