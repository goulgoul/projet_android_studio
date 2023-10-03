package com.example.gzlgln

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


class MainActivity : AppCompatActivity()
{
    private lateinit var fused_location_provider_client: FusedLocationProviderClient
    private lateinit var location_text_view: TextView
    private lateinit var weather_text_view: TextView
    private lateinit var http_client: OkHttpClient
    private var latitude: String = ""
    private var longitude: String = ""


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fused_location_provider_client = LocationServices.getFusedLocationProviderClient(this)
        location_text_view = findViewById(R.id.tvLocation)
        weather_text_view = findViewById(R.id.tvWeather)

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
                latitude = it.latitude.toString()
                longitude = it.longitude.toString()
                val textGPS = "Latitude : " + latitude + ", longitude : " + longitude
                location_text_view.text = textGPS
            }
        }
    }
    
    private fun get_weather()
    {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.INTERNET),
                100)
            return
        }
        http_client = OkHttpClient()

        val request = Request.Builder()
            .url("https://api.open-meteo.com/v1/meteofrance?latitude=$latitude&longitude=$longitude&hourly=temperature_2m")
            .build()
        http_client.newCall(request).execute().use {
                reponse -> if (!reponse.isSuccessful) throw IOException("Unexpected code $reponse")

            for ((name, value) in reponse.headers()) println("$name: $value")

            println(reponse.body!!.string())
        }

    }
}
