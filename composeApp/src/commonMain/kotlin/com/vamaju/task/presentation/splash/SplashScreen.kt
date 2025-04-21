package com.vamaju.task.presentation.splash

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import com.vamaju.task.presentation.navigation.TaskList
import com.vamaju.task.presentation.splash.composable.Logo
import com.vamaju.task.presentation.commons.composables.PermissionsRequester


@Composable
fun SplashScreen(
    logo: Painter,
) {
    val navigator = LocalNavigator.current

    var showPermissions by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.background,
            )
            .fillMaxSize()
    ) {
        /*Image(
            modifier = Modifier.fillMaxSize(),
            painter = logo,
            contentScale = ContentScale.FillBounds,
            contentDescription = "mapa"
        )*/

        val launchAnimation = remember { mutableStateOf(false) }
        val animationFinished = remember { mutableStateOf(false) }
        val logoSize by animateDpAsState(
            targetValue = if (animationFinished.value) {
                85.dp
            } else if (launchAnimation.value) 85.dp else 43.dp,
            animationSpec = tween(3000),
            finishedListener = {
                animationFinished.value = true
                launchAnimation.value = false
                showPermissions = true
            }, label = ""
        )

        Logo(
            logo = logo,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 100.dp)
                .size(logoSize)
        )

        LaunchedEffect(key1 = true) {
            launchAnimation.value = true
        }

        if (showPermissions) {

            PermissionsRequester(
                onAllGranted = {
                    navigator?.push(TaskList)
                },
            )
        }
    }
}