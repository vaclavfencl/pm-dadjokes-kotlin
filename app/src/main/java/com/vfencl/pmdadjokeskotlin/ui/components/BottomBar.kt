package com.vfencl.pmdadjokeskotlin.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.vfencl.pmdadjokeskotlin.ui.AppTab
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.outlined.Shuffle

@Composable
fun BottomBar(
    selected: AppTab,
    onSelected: (AppTab) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = selected == AppTab.RANDOM,
            onClick = { onSelected(AppTab.RANDOM) },
            icon = {
                Icon(Icons.Outlined.Shuffle, contentDescription = null)
            }
            //label = { Text("Random") }
        )

        NavigationBarItem(
            selected = selected == AppTab.CUSTOM,
            onClick = { onSelected(AppTab.CUSTOM) },
            icon = { Text("Aa", style = MaterialTheme.typography.titleLarge) }
            //label = { Text("Custom") }
        )

        NavigationBarItem(
            selected = selected == AppTab.SAVED,
            onClick = { onSelected(AppTab.SAVED) },
            icon = {
                Icon(
                    imageVector = if (selected == AppTab.SAVED) Icons.Filled.Star else Icons.Outlined.StarBorder,
                    contentDescription = null
                )
            }
        )
    }
}
