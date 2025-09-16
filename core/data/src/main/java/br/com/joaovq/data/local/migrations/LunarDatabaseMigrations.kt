package br.com.joaovq.data.local.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.joaovq.article_data.local.model.ARTICLES_TABLE_NAME
import br.com.joaovq.article_data.local.model.AUTHORS_COLUMN_NAME
import br.com.joaovq.article_data.local.model.NEXT_KEY_COLUMN_NAME
import br.com.joaovq.article_data.local.model.PREV_KEY_COLUMN_NAME
import br.com.joaovq.article_data.local.model.QUERY_COLUMN_NAME
import br.com.joaovq.article_data.local.model.REMOTE_KEYS_TABLE_NAME
import br.com.joaovq.bookmark_data.data.model.ARTICLE_ID_COLUMN_NAME
import br.com.joaovq.bookmark_data.data.model.BOOKMARK_TABLE_NAME
import br.com.joaovq.bookmark_data.data.model.ID_COLUMN_NAME

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
    val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """ALTER TABLE `$ARTICLES_TABLE_NAME` ADD COLUMN `$AUTHORS_COLUMN_NAME` TEXT NOT NULL DEFAULT '[]'""".trimIndent()
            )
            db.execSQL("""DROP VIEW IF EXISTS `ArticleWithBookmarkView`""".trimIndent())
            db.execSQL("""
                CREATE VIEW `ArticleWithBookmarkView` AS SELECT a.id as id, a.title as title, a.image_url as imageUrl, a.summary as summary, a.url as url, a.featured as featured,a.published_at as publishedAt , a.updated_at as updatedAt, a.news_site as newsSite, a.authors as authors, CASE WHEN b.id IS NOT NULL THEN 1 ELSE 0 END as isBookmark from articles_tb as a LEFT JOIN article_bookmark_tb as b ON article_id = a.id
            """.trimIndent())
        }
    }
}