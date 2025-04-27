package br.com.joaovq.lunarappcompose.data.local.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.joaovq.lunarappcompose.data.articles.model.QUERY_COLUMN_NAME
import br.com.joaovq.lunarappcompose.data.articles.model.NEXT_KEY_COLUMN_NAME
import br.com.joaovq.lunarappcompose.data.articles.model.PREV_KEY_COLUMN_NAME
import br.com.joaovq.lunarappcompose.data.articles.model.REMOTE_KEYS_TABLE_NAME

object LunarDatabaseMigrations {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("""
            CREATE TABLE IF NOT EXISTS `$REMOTE_KEYS_TABLE_NAME` (
                `$QUERY_COLUMN_NAME` TEXT PRIMARY KEY NOT NULL,
                `$PREV_KEY_COLUMN_NAME` INTEGER NULL,
                `$NEXT_KEY_COLUMN_NAME` INTEGER NULL
            )
        """.trimIndent())
        }
    }
}