package com.vamaju.task

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.vamaju.task.di.initializeKoin
import com.vamaju.task.presentation.commons.painterLogo

fun main() = application {
    initializeKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "TaskModel Manager",
        icon = painterLogo()
    ) {
        App()
    }
}