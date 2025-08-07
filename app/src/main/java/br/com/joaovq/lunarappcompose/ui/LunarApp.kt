package br.com.joaovq.lunarappcompose.ui

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import br.com.joaovq.lunarappcompose.MainViewModel
import br.com.joaovq.lunarappcompose.ui.component.LunarAppBottomNavigation
import br.com.joaovq.lunarappcompose.ui.nav.LunarNavHost

@Composable
fun LunarApp(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val mainState by mainViewModel.mainState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = modifier.imePadding(),
        bottomBar = {
            LunarAppBottomNavigation(navController = navController)
        },
        content = { innerPadding ->
            LunarNavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                onSearchResults = mainViewModel::onResultClicked,
                onReset = mainViewModel::onResetAll,
                mainState = mainState,
                getInfo = mainViewModel::getNewsSites
            )
        }
    )
}

@Preview
@Composable
private fun PreviewApp() {
    LunarApp()
}