package br.com.joaovq.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimen(
    val small: Dp = 4.dp,
    val medium: Dp = 8.dp,
    val large: Dp = 16.dp,
    val xLarge: Dp = 32.dp,
    val xxLarge: Dp = 64.dp
)

val LocalDimen = compositionLocalOf { Dimen() }