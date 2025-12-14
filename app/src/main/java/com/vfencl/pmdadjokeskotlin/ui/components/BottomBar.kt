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

@Composable
fun BottomBar(
    selected: AppTab,
    onSelected: (AppTab) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = selected == AppTab.RANDOM,
            onClick = { onSelected(AppTab.RANDOM) },
            icon = { Icon(Icons.Filled.Refresh, contentDescription = null) },
            //label = { Text("Random") }
        )

        NavigationBarItem(
            selected = selected == AppTab.CUSTOM,
            onClick = { onSelected(AppTab.CUSTOM) },
            icon = { Icon(Icons.Filled.Add, contentDescription = null) },
            //label = { Text("Add") }
        )

        NavigationBarItem(
            selected = selected == AppTab.SAVED,
            onClick = { onSelected(AppTab.SAVED) },
            icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
            //label = { Text("Saved") }
        )
    }
}
