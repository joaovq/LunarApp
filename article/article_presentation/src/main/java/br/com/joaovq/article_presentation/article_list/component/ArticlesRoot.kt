package br.com.joaovq.article_presentation.article_list.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.joaovq.article_presentation.article_list.screen.ArticlesScreen
import br.com.joaovq.article_presentation.article_list.viewmodel.ArticlesViewModel
import br.com.joaovq.ui.state.MainState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesRoot(
    modifier: Modifier = Modifier,
    articlesViewModel: ArticlesViewModel = hiltViewModel(),
    onNavigateToArticle: (Int) -> Unit = {},
    mainState: MainState = MainState(),
    onSearchResults: (List<String>) -> Unit = {},
    onReset: () -> Unit = {},
    getInfo: () -> Unit = {},
) {
    val articles = articlesViewModel.articles.collectAsLazyPagingItems()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    Box(modifier = modifier.fillMaxSize()) {
        ArticlesScreen(
            articles = articles,
            onClickArticleCard = onNavigateToArticle,
            onBookmarkChanged = articlesViewModel::onBookmarkChanged,
            onClickMenu = {
                scope.launch {
                    try {
                        showBottomSheet = true
                        sheetState.expand()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        )
        if (showBottomSheet) {
            LaunchedEffect(Unit) {
                getInfo()
            }
            SpaceArticleBottomSheet(
                mainState = mainState,
                sheetState = sheetState,
                onDismissRequest = {
                    scope.launch {
                        try {
                            sheetState.hide()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                },
                onReset = {
                    onReset()
                    scope.launch {
                        showBottomSheet = hideBottomSheet(sheetState)
                    }
                },
                onSearchResults = {
                    onSearchResults(it)
                    scope.launch {
                        showBottomSheet = hideBottomSheet(sheetState)
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private suspend fun hideBottomSheet(
    sheetState: SheetState,
): Boolean {
    try {
        sheetState.hide()
        return false
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return true
}