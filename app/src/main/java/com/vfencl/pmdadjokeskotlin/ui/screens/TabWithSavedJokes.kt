package com.vfencl.pmdadjokeskotlin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TabWithSavedJokes(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Oblíbené vtipy", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Search jokes") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Saved jokes placeholder")
    }
}
