package br.com.joaovq.lunarappcompose.onboarding.presentation.overview.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import br.com.joaovq.article_presentation.article_list.nav.ArticlesRoute
import br.com.joaovq.lunarappcompose.R
import br.com.joaovq.lunarappcompose.bookmark.presentation.nav.ArticlesBookmarkRoute
import br.com.joaovq.lunarappcompose.onboarding.presentation.search.nav.SearchRoute
import kotlinx.serialization.Serializable

data class BottomScreen(
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
    @Serializable val route: Any
)

val bottomScreens = listOf(
    BottomScreen(R.string.home, R.drawable.ic_home_smile, ArticlesRoute),
    BottomScreen(R.string.search, R.drawable.ic_search_tiny_stroke, SearchRoute),
    BottomScreen(R.string.bookmarks, R.drawable.ic_bookmark, ArticlesBookmarkRoute),
)