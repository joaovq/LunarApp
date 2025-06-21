package br.com.joaovq.article_presentation.article_list.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import br.com.joaovq.ui.state.MainState
import br.com.joaovq.ui.theme.LocalDimen
import br.com.joaovq.ui.theme.LunarTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpaceArticleBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    onDismissRequest: () -> Unit = {},
    mainState: MainState,
    onFilterClicked: (String) -> Unit = {},
    onSearchResultsClicked: () -> Unit = {}
) {
    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
    ) {
        SpaceArticleBottomSheetContent(
            mainState = mainState,
            onFilterClicked = onFilterClicked,
            onSearchResultsClicked = onSearchResultsClicked
        )
    }
}

@Composable
fun SpaceArticleBottomSheetContent(
    modifier: Modifier = Modifier,
    mainState: MainState = MainState(),
    onFilterClicked: (String) -> Unit = {},
    onSearchResultsClicked: () -> Unit = {},
) {
    val filterList = remember(mainState.filteredSites) {
        mutableStateSetOf(*mainState.filteredSites.toTypedArray())
    }
    val dimen = LocalDimen.current
    val textStyle = LocalTextStyle.current
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(dimen.large)) {
        Text(
            modifier = Modifier.padding(bottom = dimen.small),
            text = "Filter for news site",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
        if (mainState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimen.large),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            FlowColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                maxItemsInEachColumn = 3,
                horizontalArrangement = Arrangement.spacedBy(dimen.medium)
            ) {
                mainState.newsSites.fastForEach { text ->
                    val isSelected by remember(filterList) {
                        derivedStateOf {
                            filterList.contains(text)
                        }
                    }
                    ElevatedFilterChip(
                        selected = isSelected,
                        label = { Text(text) },
                        onClick = {
                            onFilterClicked(text)
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = if (isSelected) Icons.Default.Check else Icons.Default.Add,
                                null,
                                //tint = if (isSelected) MaterialTheme.colorScheme.onPrimary else LocalTextStyle.current.color
                            )
                        },
                        colors = FilterChipDefaults.elevatedFilterChipColors(
                            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
                            iconColor = textStyle.color,
                            disabledLeadingIconColor = textStyle.color,
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        ),
                    )
                }
            }
            Button(modifier = Modifier.fillMaxWidth(), onClick = onSearchResultsClicked) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Search Results".uppercase(),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}


@Preview(name = "Space Article Bottom Sheet Content", showBackground = true)
@Composable
private fun PreviewSpaceArticleBottomSheet() {
    LunarTheme(dynamicColor = false) {
        SpaceArticleBottomSheetContent(
            mainState = MainState(
                newsSites = listOf(
                    "ABC News",
                    "AmericaSpace",
                    "Nasa",
                    "CNBC",
                    "ElonX",
                    "BBC News"
                ),
                filteredSites = listOf(
                    "ABC News",
                    "AmericaSpace"
                )
            ),
        )
    }
}
