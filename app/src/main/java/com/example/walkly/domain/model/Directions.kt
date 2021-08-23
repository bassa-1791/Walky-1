package com.example.walkly.domain.model

import android.graphics.Color
import com.android.volley.Response
import com.example.walkly.BuildConfig
import com.example.walkly.R
import com.example.walkly.lib.HTTPRequest
import com.example.walkly.lib.MyApplication
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import org.json.JSONObject

/**
 * 現在地からチェックポイントまでの経路を引く
 */
class Directions {
    companion object {
        const val DEBUG_MODE = true // TODO: 本番時には消す
    }

    /**
     * 経路を引く
     *
     * @param origin 始点
     * @param place 中間地点,終点
     * @throws Exception APIキーが間違っているなどのエラー
     */
    fun drawRoute(origin: LatLng, place: MutableList<LatLng>) {
        // TODO: 正式リリース時に消す & リリースビルド時にtrueになることを確認する
        if (!BuildConfig.DEBUG || DEBUG_MODE) {
            val urlDirections = createURLDirections(origin, place)

            val listener = Response.Listener<String> { response ->
                val jsonResponse = JSONObject(response)
                if (jsonResponse.getString("status") != "OK") {
                    throw Exception(jsonResponse.getString("error_message"))
                }

                val path: MutableList<List<LatLng>> = ArrayList()
                val routes = jsonResponse.getJSONArray("routes")
                val legs = routes.getJSONObject(0).getJSONArray("legs")

                for (j in 0 until legs.length()) {
                    val steps = legs.getJSONObject(j).getJSONArray("steps")
                    for (i in 0 until steps.length()) {
                        val points =
                            steps.getJSONObject(i).getJSONObject("polyline")
                                .getString("points")
                        path.add(PolyUtil.decode(points))
                    }
                    for (i in 0 until path.size) {
                        MyApplication.getMap().addPolyline(
                            PolylineOptions().addAll(path[i]).color(Color.argb(100, 0, 0, 255))
                        )
                    }
                }
            }

            val errorListener = Response.ErrorListener {
//                throw Exception("接続が不安定です")
            }
            HTTPRequest().getRequest(urlDirections, listener, errorListener)
        }
    }

    /**
     * 経路検索用URLの作成
     *
     * @param origin 経路の始点となる場所
     * @param points 中間地点、終点となる場所
     * @throws IllegalArgumentException 配列に要素が1つもない = 終点が存在しない
     */
    private fun createURLDirections(origin: LatLng, points: MutableList<LatLng>): String {
        val apiKey: String = MyApplication.getContext().getString(R.string.google_maps_key)
        val originParam = "origin=${origin.latitude},${origin.longitude}"

        val size = points.size
        if (size <= 0) {
            throw IllegalArgumentException("引数pointsには1つ以上の要素が必要です")
        }
        var pointsParam = "&destination="
        if (size == 1) {
            pointsParam += "${points[0].latitude},${points[0].longitude}"
        } else {
            var waypointsParam = "&waypoints=${points[0].latitude},${points[0].longitude}"
            for (i in 1 until size - 1) {
                waypointsParam += "|${points[i].latitude},${points[i].longitude}"
            }
            pointsParam += "${points[size - 1].latitude},${points[size - 1].longitude}${waypointsParam}"
        }

        return "https://maps.googleapis.com/maps/api/directions/json?mode=walking&${originParam}${pointsParam}&key=${apiKey}"
    }

}