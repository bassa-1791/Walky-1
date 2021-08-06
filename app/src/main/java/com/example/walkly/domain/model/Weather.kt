package com.example.walkly.domain.model

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.example.walkly.R
import com.example.walkly.lib.HTTPRequest
import com.example.walkly.lib.MyApplication
import com.google.android.gms.maps.model.LatLng
import com.squareup.picasso.Picasso
import org.json.JSONObject

/*
* 天気予報の取得
*/
class Weather(private val activity: AppCompatActivity) {

    /**
     * 天気予報の実行
     *
     * @param latLng 現在位置
     */
    fun getForecast(latLng: LatLng) {
        val apiKey = MyApplication.getContext().getString(R.string.weather_api_key)

        val placeLat = latLng.latitude
        val placeLon = latLng.longitude

        val url = "http://api.openweathermap.org/data/2.5/weather?" +
                "lat=" + placeLat + "&" +
                "lon=" + placeLon + "&" +
                "&APPID=" + apiKey

        val listener = Response.Listener<String> { response ->
            val jsonResponse = JSONObject(response)
            val weather = jsonResponse.getJSONArray("weather")
            val item = weather.getJSONObject(0)

            val weatherIcon = item.getString("icon")

            val iconURL : String = if (weatherIcon == "") {
                "https://tsukatte.com/wp-content/uploads/2019/01/mark_kinshi.png"
            }else {
                "http://openweathermap.org/img/wn/$weatherIcon@2x.png"
            }

            Picasso.get()
                .load(iconURL)
                .resize(300, 300)
                .centerCrop()
                .into(activity.findViewById<ImageView>(R.id.imageView))
        }
        val errorListener = Response.ErrorListener {  }

        val request = HTTPRequest()
        request.getRequest(url, listener, errorListener)
    }

}

