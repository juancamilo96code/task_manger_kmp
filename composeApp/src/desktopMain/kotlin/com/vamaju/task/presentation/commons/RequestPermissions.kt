package com.vamaju.task.presentation.commons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

actual fun requestPermissions(
    onAllGranted: () -> Unit,
    onPermissionDenied: () -> Unit) : @Composable () -> Unit = {
    PermissionsRequester (
        onAllGranted = onAllGranted
    )
}

@Composable
fun PermissionsRequester(
    onAllGranted: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        onAllGranted()
    }
}