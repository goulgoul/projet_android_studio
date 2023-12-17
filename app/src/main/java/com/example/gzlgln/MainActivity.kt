package com.example.gzlgln

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.json.JSONArray
import org.json.JSONObject


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

                get_weather()
            }
        }
    }

    fun get_weather() {

        val queue = Volley.newRequestQueue(this)
        var weather_url = "https://api.open-meteo.com/v1/meteofrance?latitude=$latitude&longitude=$longitude&current=temperature_2m"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, weather_url,
            Response.Listener<String> { response ->
                var weather_json = JSONObject(response)
                var current_values = weather_json.getJSONObject("current")
                var current_temperature = current_values.get("temperature_2m")
                weather_text_view.text = "Il fait actuellement $current_temperature °C dehors."
            },
            Response.ErrorListener { weather_text_view.text = "SIKE!" })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}
