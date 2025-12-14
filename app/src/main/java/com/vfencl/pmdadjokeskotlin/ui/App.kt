package com.vfencl.pmdadjokeskotlin.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.vfencl.pmdadjokeskotlin.ui.components.BottomBar
import com.vfencl.pmdadjokeskotlin.ui.screens.TabWithCustomJokes
import com.vfencl.pmdadjokeskotlin.ui.screens.TabWithRandomJokes
import com.vfencl.pmdadjokeskotlin.ui.screens.TabWithSavedJokes

@Composable
fun App() {
    var tab by rememberSaveable { mutableStateOf(AppTab.RANDOM) }

    Scaffold(
        bottomBar = { BottomBar(selected = tab, onSelected = { tab = it }) }
    ) { innerPadding ->
        when (tab) {
            AppTab.RANDOM -> TabWithRandomJokes(Modifier.padding(innerPadding))
            AppTab.CUSTOM -> TabWithCustomJokes(Modifier.padding(innerPadding))
            AppTab.SAVED -> TabWithSavedJokes(Modifier.padding(innerPadding))
        }
    }
}
