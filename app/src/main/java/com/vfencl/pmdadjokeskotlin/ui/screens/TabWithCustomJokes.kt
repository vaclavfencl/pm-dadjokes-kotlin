package com.vfencl.pmdadjokeskotlin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vfencl.pmdadjokeskotlin.data.ServiceLocator
import kotlinx.coroutines.launch

@Composable
fun TabWithCustomJokes(modifier: Modifier = Modifier) {
    var text by rememberSaveable { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text("Napiš vlastní dad joke", style = MaterialTheme.typography.titleLarge)
            Text("Chybí ti nějaký dad joke? Ulož si ho u nás!")
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Váš vtip") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 140.dp, max = 500.dp),
                minLines = 6
            )

            val canSave = text.trim().isNotEmpty() && !isSaving

            Button(
                enabled = canSave,
                onClick = {
                    val savedText = text.trim()
                    scope.launch {
                        isSaving = true
                        try {
                            ServiceLocator.savedStore.save(savedText, source = "CUSTOM")
                            text = ""

                            val res = snackbarHostState.showSnackbar(
                                message = "Uloženo",
                                actionLabel = "Zrušit",
                                duration = SnackbarDuration.Short
                            )
                            if (res == SnackbarResult.ActionPerformed) {
                                ServiceLocator.savedStore.remove(savedText)
                                snackbarHostState.showSnackbar("Vráceno")
                            }
                        } finally {
                            isSaving = false
                        }
                    }
                }
            ) { Text("SAVE") }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 88.dp)
        )
    }
}
