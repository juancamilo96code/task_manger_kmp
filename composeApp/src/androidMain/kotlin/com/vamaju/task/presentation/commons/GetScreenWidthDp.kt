package com.vamaju.task.presentation.commons
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.runtime.Composable

@Composable
actual fun getScreenWidthDp(): Int {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp
}