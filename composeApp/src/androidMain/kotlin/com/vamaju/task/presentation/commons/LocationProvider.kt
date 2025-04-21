package com.vamaju.task.presentation.commons

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.coroutines.resumeWithException

class AndroidLocationProvider(private val context: Context) : LocationProvider {

    @OptIn(ExperimentalCoroutinesApi::class)
    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Location? = withContext(Dispatchers.IO) {
        // Verifica que el GPS esté habilitado
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Activa el GPS para continuar.", Toast.LENGTH_LONG).show()
                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })
            }
            return@withContext null
        }

        val fusedClient = LocationServices.getFusedLocationProviderClient(context)

        suspendCancellableCoroutine { cont ->
            fusedClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { location ->
                    cont.resume(
                        Location(location.latitude,location.longitude),
                        onCancellation = { }
                    )
                }
                .addOnFailureListener { exception ->
                    cont.resumeWithException(exception)
                }
        }
    }

    override suspend fun getAddressFromLocation(location: Location): Address? = withContext(Dispatchers.IO) {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        return@withContext addresses?.firstOrNull()?.getAddressLine(0)?.let { Address(it) }
    }
}

lateinit var appContext: Context

actual fun getLocationProvider(): LocationProvider = AndroidLocationProvider(appContext)