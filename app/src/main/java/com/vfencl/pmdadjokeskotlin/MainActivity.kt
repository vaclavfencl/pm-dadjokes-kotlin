package com.vfencl.pmdadjokeskotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.vfencl.pmdadjokeskotlin.ui.App
import com.vfencl.pmdadjokeskotlin.ui.theme.PMDadJokesKotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PMDadJokesKotlinTheme {
                App()
            }
        }
    }
}
