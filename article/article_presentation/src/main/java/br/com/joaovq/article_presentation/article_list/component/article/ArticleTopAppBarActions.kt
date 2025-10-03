package br.com.joaovq.article_presentation.article_list.component.article

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import br.com.joaovq.article_presentation.R
import br.com.joaovq.article_presentation.article_list.util.model.MenuItemData
import br.com.joaovq.ui.theme.Obsidian

@Composable
fun ArticleTopAppBarActions(
    expanded: Boolean,
    menuItemData: List<MenuItemData>,
    onClickShareIcon: () -> Unit = {},
    onClickInternetIcon: () -> Unit = {},
    onClickMoreVert: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    val actionsContainerColor = MaterialTheme.colorScheme.surface.copy(
        alpha = 0.6f
    )
    val actionsTintColor = if (isSystemInDarkTheme()) Color.White else Obsidian
    IconButton(
        onClick = onClickShareIcon,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = actionsContainerColor
        )
    ) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = "Share article",
            tint = actionsTintColor
        )
    }
    IconButton(
        onClick = onClickInternetIcon,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = actionsContainerColor
        )
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_internet),
            contentDescription = "See in internet",
            tint = actionsTintColor
        )
    }
    Box(modifier = Modifier) {
        IconButton(
            onClick = onClickMoreVert,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = actionsContainerColor
            )
        ) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "More actions",
                tint = actionsTintColor
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest
        ) {
            menuItemData.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.title) },
                    trailingIcon = option.icon?.let {
                        { Icon(imageVector = it, contentDescription = null) }
                    },
                    onClick = option.onClick
                )
            }
        }
    }
}