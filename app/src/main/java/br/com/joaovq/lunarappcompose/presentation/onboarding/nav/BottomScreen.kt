package br.com.joaovq.lunarappcompose.presentation.onboarding.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import br.com.joaovq.lunarappcompose.R
import br.com.joaovq.lunarappcompose.presentation.articles.nav.ArticlesRoute
import br.com.joaovq.lunarappcompose.presentation.search.nav.SearchRoute
import kotlinx.serialization.Serializable

data class BottomScreen(
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
    @Serializable val route: Any
)

val bottomScreens = listOf(
    BottomScreen(R.string.home, R.drawable.ic_home_smile, ArticlesRoute),
    BottomScreen(R.string.search, R.drawable.ic_search_tiny_stroke, SearchRoute),
    // BottomScreen(R.string.bookmarks, R.drawable.ic_bookmark, BookmarksRoute),
    //BottomScreen(R.string.profile, R.drawable.ic_profile, ProfileRoute)
)