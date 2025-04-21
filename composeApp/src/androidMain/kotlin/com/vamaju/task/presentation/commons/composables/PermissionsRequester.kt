package com.vamaju.task.presentation.commons.composables

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
actual fun PermissionsRequester(
    onAllGranted: () -> Unit,
) {

    val lifecycleOwner = LocalLifecycleOwner.current

    var observer: LifecycleEventObserver? = null

    var denied by remember {
        mutableStateOf(false)
    }

    val multiplePermissionsState = rememberMultiplePermissionsState(
        permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            listOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        } else {
            listOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    )

    DisposableEffect(key1 = lifecycleOwner) {
        observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    denied = false
                    multiplePermissionsState.permissions.map {
                        it.launchPermissionRequest()
                        if (it.status.isGranted) {
                            Log.d("PERMISSION_SUCCESS", it.permission)
                        } else if (it.status.shouldShowRationale) {
                            Log.d("PERMISSION_shouldShowRationale", it.permission)
                            it.launchPermissionRequest()
                        } else {
                            Log.d("PERMISSION_denied", it.permission)
                            it.launchPermissionRequest()
                            if (it.status.isGranted) {
                                Log.d("PERMISSION_SUCCESS", it.permission)
                            } else if (it.status.shouldShowRationale) {
                                Log.d("PERMISSION_shouldShowRationale", it.permission)
                                it.launchPermissionRequest()
                            } else {
                                Log.d("PERMISSION_denied", it.permission)
                                it.launchPermissionRequest()
                            }
                        }
                    }
                }

                else -> {

                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
        }
    }

    if (multiplePermissionsState.allPermissionsGranted) {
        observer?.let { lifecycleOwner.lifecycle.removeObserver(it) }
        LaunchedEffect(key1 = Unit) {
            onAllGranted()
        }
    }

    if (denied) {
        onAllGranted()
        denied = false
    }
}