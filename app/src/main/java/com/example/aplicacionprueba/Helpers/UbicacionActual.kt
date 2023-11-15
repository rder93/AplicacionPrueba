package com.example.aplicacionprueba.Helpers

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*

class UbicacionActual<T>(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

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
