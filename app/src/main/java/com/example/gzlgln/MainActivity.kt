package com.example.gzlgln

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class MainActivity : AppCompatActivity()
{
    private lateinit var fused_location_provider_client: FusedLocationProviderClient
    private lateinit var location_text_view: TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fused_location_provider_client = LocationServices.getFusedLocationProviderClient(this)
        location_text_view = findViewById(R.id.tvLocation)

        val boutonGPS = findViewById<Button>(R.id.getLocationButton)
        boutonGPS.setOnClickListener {
            get_location()
        }
    }

    private fun get_location()
    { // CHECK LOCATION PERMISSION
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                100)
            return
        }

        //  GET AND DISPLAY LOCATION
        val location = fused_location_provider_client.lastLocation
        location.addOnSuccessListener {
            if (it != null)
            {
                val textGPS = "Latitude : " + it.latitude.toString() + ", longitude : " + it.longitude.toString()
                location_text_view.text = textGPS
            }
        }
    }

}
