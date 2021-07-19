package com.example.walkly.application

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.walkly.domain.model.GPS
import com.example.walkly.domain.model.Place
import com.example.walkly.domain.model.Directions
import com.example.walkly.domain.model.mymap.MyMap
import com.example.walkly.lib.MyApplication
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 現在地の取得やアクティビティの開始などの指示を統括している
 */

class MapApplicationService(private val activity: AppCompatActivity) {
    companion object {
        const val ACTIVITY_INTERVAL = 60
    }
    private var previousTimeMillis: Long = 0
    private var isActivity: Boolean = false
    private var isProcess: Boolean = false
    private lateinit var directions: Directions
    private lateinit var gps: GPS
    private lateinit var place: Place

    /**
     * マップの準備ができたら現在地を取得し、GoogleMapを保管する
     *
     * @param mMap OnMapReadyCallbackインターフェースのonMapReadyが受け取る引数
     */
    fun startUp(mMap: GoogleMap) {
        gps = GPS(activity)
        gps.enableCurrentLocation(mMap)

        val myMap = MyMap(mMap)
        place = Place()
        directions = Directions()

        // TODO: MyMap()、getMyMap()を使用している箇所の修正
        val option = MarkerOptions()
        option.position(LatLng(35.1709, 136.8815))
        MyApplication.setMap(myMap)
        MyApplication.getMap().addMarker(option)
    }

    /**
     * アクティビティの開始
     */
    fun handleActivityButton() {
        if (isProcess) {
            AlertDialog.Builder(activity)
                .setTitle("処理中")
                .setMessage("しばらくお待ちください。")
                .setPositiveButton("OK") { _, _ -> }
                .show()
            return
        }
        val currentMillis = System.currentTimeMillis()
        val timeDiff = (currentMillis - previousTimeMillis) / 1000L
        if (!isActivity && timeDiff <= ACTIVITY_INTERVAL) {
            /**
             * アクティビティ中ではない(アクティビティを開始しようとしている) かつ 前回のアクティビティからN秒以内
             */
            AlertDialog.Builder(activity)
                .setTitle("適度に休憩を")
                .setMessage("適度に休憩しましょう。")
                .setPositiveButton("OK") { _, _ -> }
                .show()
            return
        }
        isProcess = true
        isActivity = !isActivity

        CoroutineScope(Dispatchers.Main).launch {
            if (isActivity) {
                val location = gps.getCurrentLocation()
                val origin = LatLng(location.latitude, location.longitude)

                val places = place.pickCheckpoint(origin)
                directions.drawRoute(origin, places)
            } else {
                MyApplication.getMap().clear()
                previousTimeMillis = currentMillis
            }
            isProcess = false
        }
    }

    /**
     * アクティビティを実行中ならマーカーを設置して、保存する(削除用)
     * そうでなければ情報ウインドウを設置する
     *
     * @param point
     */
    fun handlePointClick(point: PointOfInterest) {
        if (isActivity) {
            MyApplication.getMap().addMarker(point)
        } else {
            Toast.makeText(
                MyApplication.getContext(),
                """
            ${point.name}
            緯度:${point.latLng.latitude}
            経度:${point.latLng.longitude}
            """,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun handleMarkerClick(marker: Marker) {
        // TODO: MarkerListクラスのリファクタリング
        // TODO: アラートの検討
        MyApplication.getMap().deleteMarker(marker)
    }
}