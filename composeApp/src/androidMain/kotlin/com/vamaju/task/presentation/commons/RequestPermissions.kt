package com.vamaju.task.presentation.commons

import androidx.compose.runtime.Composable
import com.vamaju.task.presentation.commons.composables.PermissionsRequester

actual fun requestPermissions(
    onAllGranted: () -> Unit,
    onPermissionDenied: () -> Unit
): @Composable () -> Unit = {
    PermissionsRequester(
        onAllGranted = onAllGranted,
    )
}