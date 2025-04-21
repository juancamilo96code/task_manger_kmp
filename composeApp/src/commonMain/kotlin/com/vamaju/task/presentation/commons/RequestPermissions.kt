package com.vamaju.task.presentation.commons

import androidx.compose.runtime.Composable


expect fun requestPermissions(
    onAllGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) : @Composable () -> Unit