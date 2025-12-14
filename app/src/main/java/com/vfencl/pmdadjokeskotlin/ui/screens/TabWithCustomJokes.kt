package com.vfencl.pmdadjokeskotlin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TabWithCustomJokes(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Napiš vlastní dad joke", style = MaterialTheme.typography.titleLarge)
        Text("Chybí ti nějaký dad joke? Ulož si ho u nás!")

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Váš vtip") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 4
        )

        Button(onClick = { /* Uložit do paměti */ }, enabled = false) {
            Text("Uložit do oblíbených")
        }
    }
}
