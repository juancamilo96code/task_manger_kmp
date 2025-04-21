package com.vamaju.task.presentation.commons.composables

import androidx.compose.runtime.Composable

@Composable
expect fun PermissionsRequester(
    onAllGranted: () -> Unit = {}
)