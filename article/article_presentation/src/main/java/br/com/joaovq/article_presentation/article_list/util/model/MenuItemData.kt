package br.com.joaovq.article_presentation.article_list.util.model

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItemData(
    val title: String,
    val icon: ImageVector? = null,
    val onClick: () -> Unit
)
