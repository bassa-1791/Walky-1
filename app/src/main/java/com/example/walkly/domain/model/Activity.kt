package com.example.walkly.domain.model

import com.android.volley.Response
import com.example.walkly.R
import com.example.walkly.lib.HTTPRequest
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONObject
import kotlin.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * 目的地のマーカー表示
 */
class Activity(private val mMap: GoogleMap) {
    private var isActivity: Boolean = false
    private val places: MutableList<LatLng> = ArrayList()

    fun toggleIsActivity() {
        isActivity = !isActivity
    }

    fun getIsActivity(): Boolean {
        return isActivity
    }

    /**
     * 周辺施設の位置情報を取得する
     *
     * @param origin 現在地
     * @throws Exception APIキーが間違っているなどのエラー
     */
    suspend fun startActivity(origin: LatLng): MutableList<LatLng> {
        return suspendCoroutine { continuation ->
            // TODO: リストの初期化
            // TODO: debug後で消す
            if (true) {
                places.clear()
                val url = createURLPlacaes(origin, "restaurant")
                val listener = Response.Listener<String> { response ->
                    val jsonResponse = JSONObject(response)
                    try {
                        val results = jsonResponse.getJSONArray("results")
                        for (i in 0 until results.length()) {
                            val item = results.getJSONObject(i)
                            val geometry = item.getJSONObject("geometry")
                            val location = geometry.getJSONObject("location")

                            val latLng =
                                LatLng(location.getDouble("lat"), location.getDouble("lng"))
                            places.add(latLng)
                        }
                    } catch (e: Exception) {
                        throw Exception(jsonResponse.getString("error_message"))
                    } finally {
                        val random = randomCheckpoint(places)
                        for (item in random) {
                            val options = MarkerOptions()
                            options.position(item)
                            options.title("やばい")
                            options.icon(
                                BitmapDescriptorFactory.defaultMarker(
                                    BitmapDescriptorFactory.HUE_BLUE
                                )
                            )
                            mMap.addMarker(options)
                        }
                        continuation.resume(random)
                    }
                }
                val errorListener = Response.ErrorListener {
                    continuation.resume(places)
                }
                HTTPRequest().getRequest(url, listener, errorListener)
            } else {
                // TODO: debug後で消す
                continuation.resume(places)
            }
        }
    }

    /**
     * URL作成
     *
     * @param origin 現在地
     * @param types 施設種類
     */
    private fun createURLPlacaes(origin: LatLng, types: String): String {
        // TODO: タイプをランダムにする?
        return "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${origin.latitude},${origin.longitude}&radius=2500&type=${types}&key=${
            MyApplication.getContext().getString(
                R.string.google_maps_key
            )
        }"
    }

    /**
     * ランダムで選択
     *
     * @param places
     */
    fun randomCheckpoint(places: MutableList<LatLng>): MutableList<LatLng> {
        val randomPlace = places.shuffled()
        return randomPlace.take(5).toMutableList()
    }
}