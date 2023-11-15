package com.example.aplicacionprueba.Helpers

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*

class LocationHelper<T>(private val context: Context) {

    private var latitud: String = ""
    private var longitud: String = ""

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
        .setWaitForAccurateLocation(true)
        .build()

    @SuppressLint("MissingPermission")
    fun obtenerUbicacion(callback: (T?) -> Unit) {

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                for (location in locationResult.locations) {
                    latitud = location.latitude.toString()
                    longitud = location.longitude.toString()
                    fusedLocationClient.removeLocationUpdates(this)
                    callback(location as T?)
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)

    }

}
