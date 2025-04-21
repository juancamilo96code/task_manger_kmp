package com.vamaju.task.presentation.commons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

@Composable
actual fun painterLogo(): Painter = painterResource("images/logo.png")

@Composable
actual fun painterToDoIcon(): Painter = painterResource("images/list_to_do.png")

@Composable
actual fun painterDoneIcon(): Painter = painterResource("images/done_collection.png")
