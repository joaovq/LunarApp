package br.com.joaovq.article_presentation.article_list.component.article_list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateSet
import androidx.compose.ui.text.TextStyle

@Composable
fun NewsSiteFilterChip(
    isSelected: Boolean,
    text: String,
    filterList: SnapshotStateSet<String>,
    textStyle: TextStyle,
) {
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