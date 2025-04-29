package br.com.joaovq.lunarappcompose.data.articles.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val ARTICLES_TABLE_NAME = "articles_tb"

@Entity(tableName = ARTICLES_TABLE_NAME)
class ArticleEntity(
    @PrimaryKey
    val id: Int = 0,
    val featured: Boolean,
    @ColumnInfo("image_url")
    val imageUrl: String,
    @ColumnInfo("news_site")
    val newsSite: String,
    @ColumnInfo("published_at")
    val publishedAt: String,
    val summary: String,
    val title: String,
    @ColumnInfo("updated_at")
    val updatedAt: String,
    val url: String
)