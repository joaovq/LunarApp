package br.com.joaovq.article_presentation.article_list.component.article

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ArticleTopAppBarActions(
    expanded: Boolean,
    menuItemData: List<String>,
    onClickMoreVert: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    /*IconButton(onClick = {}) {
        Icon(
            imageVector = Icons.Outlined.PlayCircle,
            contentDescription = null,
            tint = Color.White
        )
    }
    IconButton(onClick = {}) {
        Icon(
            imageVector = Icons.Outlined.Share,
            contentDescription = null,
            tint = Color.White
        )
    }*/
    Box(modifier = Modifier) {
        IconButton(onClick = onClickMoreVert) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = null,
                tint = Color.White
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest
        ) {
            menuItemData.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {}
                )
            }
        }
    }
}