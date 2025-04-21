package com.vamaju.task.presentation.commons


import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getScreenWidthDp(): Int {
    // Puedes usar el tamaño del window o tamaño real del contenido
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current

    val widthPx = windowInfo.containerSize.width
    return with(density) { widthPx.toDp().value.toInt() }
}