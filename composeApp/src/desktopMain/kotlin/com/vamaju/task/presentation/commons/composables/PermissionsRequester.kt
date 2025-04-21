package com.vamaju.task.presentation.commons.composables

import androidx.compose.runtime.Composable

@Composable
actual fun PermissionsRequester(
    onAllGranted: () -> Unit
) {
    onAllGranted()
}