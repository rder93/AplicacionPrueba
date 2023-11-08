package com.example.aplicacionprueba

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class UbicacionActual<T>(private val context: Context) {

    val permissionFineLocation =
        android.Manifest.permission.ACCESS_FINE_LOCATION // Cambia esto al permiso que necesites
    val permissionCoarseLocation =
        android.Manifest.permission.ACCESS_COARSE_LOCATION // Cambia esto al permiso que necesites

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest: LocationRequest.Builder =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)

    @SuppressLint("MissingPermission")
    fun obtenerUbicacion(callback: (T?) -> Unit) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                callback(location as T?)
            }
            .addOnFailureListener {
                callback(null)
            }

    }

}
