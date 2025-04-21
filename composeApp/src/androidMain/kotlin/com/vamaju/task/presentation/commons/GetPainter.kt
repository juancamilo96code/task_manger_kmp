package com.vamaju.task.presentation.commons

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.painter.Painter
import com.vamaju.task.R

@Composable
actual fun painterLogo(): Painter = painterResource(id = R.drawable.logo)

@Composable
actual fun painterToDoIcon(): Painter = painterResource(id = R.drawable.list_to_do)

@Composable
actual fun painterDoneIcon(): Painter = painterResource(id = R.drawable.done_collection)
