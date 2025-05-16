package br.com.joaovq.article_data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val ARTICLES_TABLE_NAME = "articles_tb"
const val ID_COLUMN_NAME = "id"
const val IMAGE_URL_COLUMN_NAME = "image_url"
const val NEWS_SITE_COLUMN_NAME = "news_site"
const val PUBLISHED_AT_COLUMN_NAME = "published_at"
const val SUMMARY_COLUMN_NAME = "summary"
const val TITLE_COLUMN_NAME = "title"
const val UPDATED_AT_COLUMN_NAME = "updated_at"
const val URL_COLUMN_NAME = "url"

@Entity(tableName = ARTICLES_TABLE_NAME)
class ArticleEntity(
    @PrimaryKey
    @ColumnInfo(ID_COLUMN_NAME)
    val id: Int = 0,
    val featured: Boolean,
    @ColumnInfo(IMAGE_URL_COLUMN_NAME)
    val imageUrl: String,
    @ColumnInfo(NEWS_SITE_COLUMN_NAME)
    val newsSite: String,
    @ColumnInfo(PUBLISHED_AT_COLUMN_NAME)
    val publishedAt: String,
    @ColumnInfo(SUMMARY_COLUMN_NAME)
    val summary: String,
    @ColumnInfo(TITLE_COLUMN_NAME)
    val title: String,
    @ColumnInfo(UPDATED_AT_COLUMN_NAME)
    val updatedAt: String,
    @ColumnInfo(URL_COLUMN_NAME)
    val url: String
)