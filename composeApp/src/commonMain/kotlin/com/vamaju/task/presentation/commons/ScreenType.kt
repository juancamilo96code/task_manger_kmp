package com.vamaju.task.presentation.commons

import androidx.compose.runtime.Composable

enum class ScreenType {
    Small, Medium, Large
}

@Composable
fun rememberScreenType(): ScreenType {

    return when (getScreenWidthDp()) {
        in 0..599 -> ScreenType.Small
        in 600..839 -> ScreenType.Medium
        else -> ScreenType.Large
    }
}