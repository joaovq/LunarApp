package br.com.joaovq.article_presentation.article_list.component.article

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import br.com.joaovq.article_domain.model.Article
import br.com.joaovq.article_presentation.R
import br.com.joaovq.ui.theme.LocalDimen
import br.com.joaovq.ui.theme.Obsidian
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.toBitmap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleTopAppBar(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    article: Article?,
    actions: @Composable () -> Unit,
) {
    var topBarContrastColor by remember { mutableStateOf(Color.White) }
    val context = LocalContext.current
    val dimen = LocalDimen.current
    Box(
        modifier = Modifier.heightIn(max = 250.dp)
    ) {
        val asyncImagePainter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context)
                .data(article?.imageUrl)
                .allowHardware(false).build(),
            error = painterResource(R.drawable.ic_launcher_background),
            contentScale = ContentScale.Crop,
            onSuccess = { state ->
                topBarContrastColor = getContrastColor(state = state)
            }
        )
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = asyncImagePainter,
            contentDescription = "${article?.title}",
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
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
            Text(
                modifier = Modifier
                    .padding(horizontal = dimen.large, vertical = 20.dp),
                text = article?.title.orEmpty(),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = topBarContrastColor
                ),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

private fun getContrastColor(state: AsyncImagePainter.State.Success): Color {
    val bitmap = state.result.image.toBitmap()
    val palette = Palette.from(bitmap)
        .clearFilters().generate()
    val dominant = palette.getDominantColor(0xFFFFFF)
    val luminance = Color(dominant).luminance()
    return if (luminance > 0.5f) {
        Color.DarkGray
    } else {
        Color.White
    }
}