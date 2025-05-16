package br.com.joaovq.article_data.local.view

import androidx.room.DatabaseView
import br.com.joaovq.article_data.local.model.ARTICLES_TABLE_NAME

@DatabaseView(
    "SELECT a.id as id, a.title as title, a.image_url as imageUrl, a.summary as summary, a.url as url," +
            " a.featured as featured,a.published_at as publishedAt ," +
            " a.updated_at as updatedAt, a.news_site as newsSite," +
            " CASE WHEN b.id IS NOT NULL THEN 1 ELSE 0 END as isBookmark from " +
            "$ARTICLES_TABLE_NAME as a LEFT JOIN article_bookmark_tb as b ON article_id = a.id"
)
class ArticleWithBookmarkView(
    val id: Int = 0,
    val featured: Boolean,
    val imageUrl: String,
    val url: String,
    val summary: String,
    val isBookmark: Boolean,
    val title: String,
    val newsSite: String,
    val updatedAt: String,
    val publishedAt: String,
)