package br.com.joaovq.lunarappcompose.article.presentation.article_list.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import br.com.joaovq.article_presentation.R
import br.com.joaovq.lunarappcompose.R
import br.com.joaovq.lunarappcompose.core.ui.utils.ext.shimmerEffect
import br.com.joaovq.lunarappcompose.ui.theme.LocalDimen
import br.com.joaovq.lunarappcompose.ui.theme.Obsidian

@Composable
fun ArticleCardShimmerItem(
    modifier: Modifier = Modifier
) {
    val dimen = LocalDimen.current
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) Obsidian else Color.White,
            contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimen.large),
            verticalArrangement = Arrangement.spacedBy(dimen.medium)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_article_card)))
                    .shimmerEffect(),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_article_card)))
                    .shimmerEffect()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .height(30.dp)
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_article_card)))
                    .shimmerEffect()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(.7f)
                    .height(40.dp)
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_article_card)))
                    .shimmerEffect()
            )
        }
    }
}