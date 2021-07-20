package com.smarthings.data.connectivity

import android.content.Context
import android.location.Geocoder
import java.io.IOException

class LocationCoderImpl(
    private val context: Context,
    private val connectivity: Connectivity
) : LocationCoder {

    private val preferences = context
        .getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)

    override fun search(location: String): Location {
        if (connectivity.hasNetworkAccess()) {
            try {
                Geocoder(context).getFromLocationName(
                    location,
                    GEO_CODER_MAX_RESULTS
                ).firstOrNull()?.let { address ->
                    preferences.edit().apply {
                        putFloat(LAST_LATITUDE, address.latitude.toFloat())
                        putFloat(LAST_LONGITUDE, address.longitude.toFloat())
                        apply()
                    }
                    return Location(
                        address.latitude,
                        address.longitude,
                        location
                    )
                } ?: kotlin.run {
                    return lastKnownLocation(location)
                }
            } catch (e: IOException) {
                return lastKnownLocation(location)
            }

        } else {
            return lastKnownLocation(location)
        }
    }

    private fun lastKnownLocation(location: String) = Location(
        preferences.getFloat(LAST_LATITUDE, DEFAULT_LATITUDE).toDouble(),
        preferences.getFloat(LAST_LONGITUDE, DEFAULT_LONGITUDE).toDouble(),
        location
    )

    companion object {
        private const val PREF_KEY = "LOCATION_PREFERENCES"
        private const val LAST_LATITUDE = "LAST_LATITUDE"
        private const val LAST_LONGITUDE = "LAST_LONGITUDE"
        private const val DEFAULT_LATITUDE = 13.69f
        private const val DEFAULT_LONGITUDE = -89.19f
        private const val GEO_CODER_MAX_RESULTS = 1
    }
}