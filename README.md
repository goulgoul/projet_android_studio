# LocMétéo

LocMétéo est une application donnant la météo en fonction de votre localisation. Elle possède deux thèmes différents, un thème clair pour la journée et un thème sombre pour la nuit. 

Le fonctionnement de l'application est très simple. Il y a un bouton au centre de l'écran, et lorsque l'utilisateur appuie dessus l'écran affiche la température extérieure.

## Acquisition des coordonnées de l'utilisateur

Pour fonctionner l'application récupère les coordonnées de localisation à l'aide du GPS du smartphone.
Dans un premier temps nous vérifions que l'application possède les droits d'accès à la localisation du smartphone. Et si l'application de ne les possède pas, nous demandons à l'utilisatuer s'il veut les activer.
```kt
// CHECK LOCATION PERMISSION
if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
    && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
{
    ActivityCompat.requestPermissions(this,
        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
        100)
    return
}
```

## Acquisition de la température par l'API OpenMeteo de Météo France

Une fois que l'applications possèdent les permissions nécessaires pour récupérer les coordonnées, nous les stockons dans les variables `latitude` et `longitude`.
```kt
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
```


Enfin, nous faisons une requête HTTP à l'API d'Open Meteo, afin d'obtenir les conditions météorologiques de nos coordonnées.
```kt
val queue = Volley.newRequestQueue(this)
weather_url = "https://api.open-meteo.com/v1/meteofrance?latitude=$latitude&longitude=$longitude&current=temperature_2m"

```

Une fois l'acquisition des données météorologiques réalisée, nous affichons à l'écran la température.
```kt
val stringRequest = StringRequest(Request.Method.GET, weather_url,
Response.Listener<String> { response ->
    var weather_json = JSONObject(response)
    var current_values = weather_json.getJSONObject("current")
    var current_temperature = current_values.get("temperature_2m")
    weather_text_view.text = "Il fait actuellement $current_temperature °C dehors."
},
queue.add(stringRequest)

```




