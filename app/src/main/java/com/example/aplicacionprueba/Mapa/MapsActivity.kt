package com.example.aplicacionprueba.Mapa

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.aplicacionprueba.Helpers.UbicacionActual

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.aplicacionprueba.R
import com.example.aplicacionprueba.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var ubicacionActual: UbicacionActual<Location>

    private val CODIGO_SOLICITUD_UBICACION = 100

    val permissionFineLocation =
        android.Manifest.permission.ACCESS_FINE_LOCATION // Cambia esto al permiso que necesites
    val permissionCoarseLocation =
        android.Manifest.permission.ACCESS_COARSE_LOCATION // Cambia esto al permiso que necesites

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        ubicacionActual = UbicacionActual<Location>(this)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    override fun onStart() {
        super.onStart()
        if (validarPermisos()) {
            obtenerUbicacion()
        } else {
            pedirPermisos()
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
            if (ubicacion != null && mMap != null) {
                // Haz algo con la ubicación actual
                val latitud = ubicacion.latitude
                val longitud = ubicacion.longitude
                println("Latitud: $latitud, Longitud: $longitud")
                val posicion = LatLng(latitud, longitud)
                mMap.addMarker(MarkerOptions().position(posicion).title("HERE!"))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(posicion))
            } else {
                println("No se pudo obtener la ubicación actual o los permisos no están habilitados.")
            }
        }
    }
}