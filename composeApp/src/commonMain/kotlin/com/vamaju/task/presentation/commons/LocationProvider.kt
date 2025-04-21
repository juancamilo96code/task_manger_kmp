package com.vamaju.task.presentation.commons

data class Location(val latitude: Double, val longitude: Double)
data class Address(val fullAddress: String)

interface LocationProvider {
    suspend fun getCurrentLocation(): Location?
    suspend fun getAddressFromLocation(location: Location): Address?
}

expect fun getLocationProvider(): LocationProvider