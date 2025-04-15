package br.com.joaovq.lunarappcompose.presentation.screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import br.com.joaovq.lunarappcompose.R
import br.com.joaovq.lunarappcompose.domain.model.Article
import br.com.joaovq.lunarappcompose.presentation.component.ArticleCard
import br.com.joaovq.lunarappcompose.ui.theme.LunarColors
import br.com.joaovq.lunarappcompose.ui.theme.LunarTheme
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesScreen(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Icon app"
                        )
                    }
                },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.ic_moon),
                            contentDescription = "Icon app"
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = stringResource(R.string.app_name),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = if (isSystemInDarkTheme()) {
                    LunarColors.bottomNavigationBackgroundDark
                } else {
                    LunarColors.bottomNavigationBackgroundLight
                }
            ) {
                NavigationBarItem(
                    true,
                    onClick = {},
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_home_smile),
                            contentDescription = "home icon"
                        )
                    },
                    label = {
                        Text("Home")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary
                    )
                )
                NavigationBarItem(
                    false,
                    onClick = {},
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_search_tiny_stroke),
                            contentDescription = "search icon"
                        )
                    },
                    label = {
                        Text("Search")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary
                    )
                )
                NavigationBarItem(
                    false,
                    onClick = {},
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_bookmark),
                            contentDescription = "bookmark icon"
                        )
                    },
                    label = {
                        Text("Bookmarks")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary
                    )
                )
                NavigationBarItem(
                    false,
                    onClick = {},
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_profile),
                            contentDescription = "bookmark icon"
                        )
                    },
                    label = {
                        Text("Profile")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary
                    )
                )

            }
        }
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            isRefreshing = articles.loadState.refresh is LoadState.Loading,
            onRefresh = { articles.refresh() },
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(articles.itemCount, key = articles.itemKey()) { i ->
                    val article = articles[i] ?: return@items
                    ArticleCard(article = article)
                }
                item {
                    when {
                        articles.loadState.append.endOfPaginationReached -> {
                            Text(text = "No more articles to load")
                        }

                        articles.loadState.append is LoadState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        articles.loadState.hasError -> Text(text = "Error to load articles")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewArticlesScreen() {
    LunarTheme(dynamicColor = false) {
        ArticlesScreen(articles = flowOf(PagingData.from(listOf<Article>())).collectAsLazyPagingItems())
    }
}