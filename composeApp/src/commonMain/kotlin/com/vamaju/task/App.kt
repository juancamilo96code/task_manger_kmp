package com.vamaju.task

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.vamaju.task.presentation.navigation.Splash
import com.vamaju.task.presentation.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    AppTheme {
        Navigator(Splash)
    }
}