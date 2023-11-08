package com.example.aplicacionprueba

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.aplicacionprueba.databinding.ActivityUbicacionBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import java.security.Permission

class UbicacionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUbicacionBinding

    private val CODIGO_SOLICITUD_UBICACION = 100

    val permissionFineLocation =
        android.Manifest.permission.ACCESS_FINE_LOCATION // Cambia esto al permiso que necesites
    val permissionCoarseLocation =
        android.Manifest.permission.ACCESS_COARSE_LOCATION // Cambia esto al permiso que necesites

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var ubicacionActual: UbicacionActual<Location>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUbicacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        ubicacionActual = UbicacionActual<Location>(this)
    }

    override fun onStart() {
        super.onStart()

        if (validarPermisos()) {
            obtenerUbicacion()
        } else {
            pedirPermisos()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            CODIGO_SOLICITUD_UBICACION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    obtenerUbicacion()
                } else {
                    Toast.makeText(
                        this,
                        "Se necesita aceptar los permisos de ubicacion para usar la app.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun pedirPermisos() {
        val racional =
            ActivityCompat.shouldShowRequestPermissionRationale(this, permissionFineLocation)
        if (racional) {
            solicitarPermisos()
        } else {
            solicitarPermisos()
        }
    }

    private fun solicitarPermisos() {
        requestPermissions(
            arrayOf(permissionCoarseLocation, permissionFineLocation),
            CODIGO_SOLICITUD_UBICACION
        )
    }

    private fun validarPermisos(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permissionFineLocation
        ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
            this,
            permissionCoarseLocation
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun obtenerUbicacion() {
        ubicacionActual.obtenerUbicacion { ubicacion ->
            if (ubicacion != null) {
                // Haz algo con la ubicación actual
                val latitud = ubicacion.latitude
                val longitud = ubicacion.longitude
                println("Latitud: $latitud, Longitud: $longitud")

                binding.latitudTV.text = latitud.toString()
                binding.longitudTV.text = longitud.toString()
            } else {
                println("No se pudo obtener la ubicación actual o los permisos no están habilitados.")
            }
        }
    }
}