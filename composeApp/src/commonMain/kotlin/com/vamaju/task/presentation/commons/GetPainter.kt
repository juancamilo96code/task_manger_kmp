package com.vamaju.task.presentation.commons


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

@Composable
expect fun painterLogo(): Painter

@Composable
expect fun painterToDoIcon(): Painter

@Composable
expect fun painterDoneIcon(): Painter