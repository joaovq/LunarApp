package br.com.joaovq.lunarappcompose.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import br.com.joaovq.ui.theme.LunarColors
import br.com.joaovq.lunarappcompose.ui.nav.bottomScreens

@Composable
fun LunarAppBottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val shouldShowBottomBar = bottomScreens.any { route ->
        currentDestination?.hierarchy?.any { it.hasRoute(route.route::class) } == true
    }
    AnimatedVisibility(
        visible = shouldShowBottomBar,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        NavigationBar(
            modifier = modifier,
            containerColor = if (isSystemInDarkTheme()) {
                LunarColors.bottomNavigationBackgroundDark
            } else {
                LunarColors.bottomNavigationBackgroundLight
            }
        ) {
            bottomScreens.forEach { screen ->
                NavigationBarItem(
                    currentDestination?.hierarchy?.any {
                        it.hasRoute(screen.route::class)
                    } == true, onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }, icon = {
                        Icon(
                            painter = painterResource(screen.icon),
                            contentDescription = "${stringResource(screen.label)} icon"
                        )
                    }, label = {
                        Text(stringResource(screen.label))
                    }, colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    }
}