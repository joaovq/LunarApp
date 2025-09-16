package br.com.joaovq.article_data.local.model

import androidx.room.Embedded
import androidx.room.Relation
import br.com.joaovq.bookmark_data.data.model.ARTICLE_ID_COLUMN_NAME
import br.com.joaovq.bookmark_data.data.model.ArticleBookmarkEntity

data class ArticleAndBookmark(
    @Embedded val articleEntity: ArticleEntity,
    @Relation(
        parentColumn = ID_COLUMN_NAME,
        entityColumn = ARTICLE_ID_COLUMN_NAME
    )
    val bookmarkEntity: ArticleBookmarkEntity?
)