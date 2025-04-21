package com.vamaju.task.presentation.splash.composable

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun Logo(modifier: Modifier, logo: Painter) {
    Image(
        painter = logo,
        contentDescription = "eMovie",
        modifier = modifier,
    )
}