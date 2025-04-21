package com.vamaju.task.presentation.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.vamaju.task.presentation.commons.painterLogo
import com.vamaju.task.presentation.splash.SplashScreen
import com.vamaju.task.presentation.task_details.TaskDetailsScreen
import com.vamaju.task.presentation.task_list.TaskListScreen

object Splash : Screen {
    @Composable
    override fun Content() {
        SplashScreen(
            logo = painterLogo(),
        )
    }
}

object TaskList : Screen {
    @Composable
    override fun Content() = TaskListScreen()
}

data class TaskDetails(val taskId: Long) : Screen {
    @Composable
    override fun Content() = TaskDetailsScreen(taskId)
}