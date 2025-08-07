package br.com.joaovq.article_presentation.article_list.component

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
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
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
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
    onReset: () -> Unit = {},
    onSearchResults: (List<String>) -> Unit = {}
) {
    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
    ) {
        SpaceArticleBottomSheetContent(
            mainState = mainState,
            onSearchResults = onSearchResults,
            onReset = onReset
        )
    }
}

@Composable
fun SpaceArticleBottomSheetContent(
    modifier: Modifier = Modifier,
    mainState: MainState = MainState(),
    onReset: () -> Unit = {},
    onSearchResults: (List<String>) -> Unit = {},
) {
    val filterList = remember(mainState.filteredSites) {
        mutableStateSetOf(*mainState.filteredSites.toTypedArray())
    }
    val dimen = LocalDimen.current
    val textStyle = LocalTextStyle.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimen.large)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(bottom = dimen.small),
                text = buildString {
                    append("Filter for news site ")
                    append("(${filterList.size})")
                },
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
            AnimatedVisibility(visible = filterList.isNotEmpty()) {
                BasicText(
                    text = buildAnnotatedString {
                        withLink(
                            LinkAnnotation.Clickable(
                                "reset-all-text",
                                linkInteractionListener = { _ ->
                                    onReset()
                                }
                            )
                        ) {
                            append("Reset all")
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
                )
            }
        }
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
                    .padding(vertical = dimen.small)
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
                            if (filterList.contains(text)) {
                                filterList.remove(text)
                            } else {
                                filterList.add(text)
                            }
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = if (isSelected) Icons.Default.Check else Icons.Default.Add,
                                null,
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
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onSearchResults(filterList.toList()) }) {
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
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    name = "Space Article Bottom Sheet Content Dark", backgroundColor = 0xFF1E1F22,
)
@Composable
private fun PreviewSpaceArticleBottomSheet() {
    LunarTheme(dynamicColor = false) {
        SpaceArticleBottomSheetContent(
            mainState = MainState(
                newsSites = listOf(
                    "ABC News", "AmericaSpace", "Nasa", "CNBC", "ElonX", "BBC News"
                ), filteredSites = listOf(
                    "ABC News", "AmericaSpace"
                )
            ),
        )
    }
}
