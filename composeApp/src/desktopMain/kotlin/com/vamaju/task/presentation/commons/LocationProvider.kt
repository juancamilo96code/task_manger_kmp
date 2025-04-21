package com.vamaju.task.presentation.commons

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.floatOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.net.URL

class DesktopLocationProvider : LocationProvider {
    override suspend fun getCurrentLocation(): Location? = withContext(Dispatchers.IO) {
        try {
            val json = URL("http://ip-api.com/json/").readText()
            val jsonObj = Json.parseToJsonElement(json).jsonObject
            val lat = jsonObj["lat"]?.jsonPrimitive?.floatOrNull ?: return@withContext null
            val lon = jsonObj["lon"]?.jsonPrimitive?.floatOrNull ?: return@withContext null
            Location(lat.toDouble(), lon.toDouble())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getAddressFromLocation(location: Location): Address? = withContext(Dispatchers.IO) {
        try {
            val json = URL("https://nominatim.openstreetmap.org/reverse?format=json&lat=${location.latitude}&lon=${location.longitude}").readText()
            val jsonObj = Json.parseToJsonElement(json).jsonObject
            val displayName = jsonObj["display_name"]?.jsonPrimitive?.content
            displayName?.let { Address(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

actual fun getLocationProvider(): LocationProvider = DesktopLocationProvider()