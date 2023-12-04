package com.example.gzlgln

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.HttpClientBuilder
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.EntityUtils



class MainActivity : AppCompatActivity()
{
    private lateinit var fused_location_provider_client: FusedLocationProviderClient
    private lateinit var location_text_view: TextView
    private lateinit var weather_text_view: TextView
    private var latitude: String = ""
    private var longitude: String = ""
    private var temperature: Float = 0.0f


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
        var weather_url = "https://api.open-meteo.com/v1/meteofrance?latitude=$latitude&longitude=$longitude&hourly=temperature_2m"
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.INTERNET),
                100)
            return
        }
        val httpclient: HttpClient = HttpClientBuilder.create().build()
        val httpget = HttpGet(weather_url)

        val response: HttpResponse = httpclient.execute(httpget)

        if (response.getStatusLine().getStatusCode() === 200)
        {
            val server_response: String = EntityUtils.toString(response.getEntity())
            Log.i("Server response", server_response)
        }
        else
        {
            Log.i("Server response", "Failed to get server response")
        }
    }
}
