package br.com.joaovq.article_presentation.article_list.component.article

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.com.joaovq.ui.theme.Obsidian

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleTopAppBarContainer(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    actions: @Composable () -> Unit,
) {
    Column {
        Spacer(modifier = modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        TopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = onNavigateUp,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(
                            alpha = 0.6f
                        )
                    )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIosNew,
                        contentDescription = null,
                        tint = if (isSystemInDarkTheme()) Color.White else Obsidian
                    )
                }
            },
            actions = { actions() },
            title = {},
            windowInsets = WindowInsets(
                top = 0.dp,
                bottom = 0.dp
            ),
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
        )
    }
}