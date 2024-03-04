package com.deneme.weathermate

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.Locale

class SplashActivity : AppCompatActivity() {
    private var nameofcity = "DefaultCity"
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getLocation()
    }

    private fun getLocation() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                getCityNameByCoordinates(this, location.latitude, location.longitude)
            } else {

                startMainActivity()
            }
        }
    }

    private fun getCityNameByCoordinates(context: Context, latitude: Double, longitude: Double) {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                nameofcity = addresses[0].adminArea ?: "DefaultCity"
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            startMainActivity()
        }
    }


    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("CITY_NAME", nameofcity)
        startActivity(intent)
        finish()
    }
}
