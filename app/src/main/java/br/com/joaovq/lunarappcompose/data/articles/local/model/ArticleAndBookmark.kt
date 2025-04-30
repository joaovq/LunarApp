package br.com.joaovq.lunarappcompose.data.articles.local.model

import androidx.room.Embedded
import androidx.room.Relation
import br.com.joaovq.lunarappcompose.data.bookmark.model.ARTICLE_ID_COLUMN_NAME
import br.com.joaovq.lunarappcompose.data.bookmark.model.ArticleBookmarkEntity

data class ArticleAndBookmark(
    @Embedded val articleEntity: ArticleEntity,
    @Relation(
        parentColumn = ID_COLUMN_NAME,
        entityColumn = ARTICLE_ID_COLUMN_NAME
    )
    val bookmarkEntity: ArticleBookmarkEntity?
)