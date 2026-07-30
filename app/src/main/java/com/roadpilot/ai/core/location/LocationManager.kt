package com.roadpilot.ai.core.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.*

class LocationManager(
    context: Context
) {

    private val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val locationRequest =
        LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            1000L
        )
            .setMinUpdateIntervalMillis(500L)
            .build()

    private var callback: LocationCallback? = null

    var onLocationChanged: ((Location) -> Unit)? = null

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {

        if (callback != null) return

        callback = object : LocationCallback() {

            override fun onLocationResult(result: LocationResult) {

                result.lastLocation?.let {

                    onLocationChanged?.invoke(it)
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            callback!!,
            null
        )
    }

    fun stopLocationUpdates() {

        callback?.let {

            fusedLocationClient.removeLocationUpdates(it)
        }

        callback = null
    }
}