package com.vfencl.pmdadjokeskotlin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TabWithRandomJokes(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Náhodný dad joke", style = MaterialTheme.typography.titleLarge)
        Text("RANDOM DAD JOKE PLACEHOLDER", style = MaterialTheme.typography.bodyMedium)

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(onClick = { /* Generuj nový vtip */ }) { Text("GENERATE NEW JOKE") }
            Button(onClick = { /* CTRLC */ }) { Text("COPY ICON") }
            Button(onClick = { /* Ulož do paměti */ }) { Text("HVĚZDIČKA ICON") }
        }
    }
}
