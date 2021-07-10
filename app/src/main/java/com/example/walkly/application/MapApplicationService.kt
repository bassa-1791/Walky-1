package com.example.walkly.application

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.walkly.domain.model.GPS
import com.example.walkly.domain.model.Place
import com.example.walkly.domain.model.Route
import com.example.walkly.domain.model.mymap.MyMap
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.PointOfInterest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 現在地の取得やアクティビティの開始などの指示を統括している
 */

class MapApplicationService(private val activity: AppCompatActivity) {
    private lateinit var myMap: MyMap
    private lateinit var route: Route
    private lateinit var gps: GPS
    private lateinit var place: Place // TODO: 名前
    private var isActivity: Boolean = false
    private var isProcess: Boolean = false

    /**
     * マップの準備ができたら現在地を取得し、GoogleMapを保管する
     *
     * @param mMap OnMapReadyCallbackインターフェースのonMapReadyが受け取る引数
     */
    fun startUp(mMap: GoogleMap) {
        gps = GPS(activity)
        gps.enableCurrentLocation(mMap)
        myMap = MyMap(mMap)
        place = Place(mMap)
        route = Route(mMap)
    }

    /**
     * アクティビティの開始
     */
    fun handleActivityButton() {
        // TODO: クリック間隔による制限を追加する?
        if (isProcess) {
            AlertDialog.Builder(activity)
                .setTitle("処理中")
                .setMessage("しばらくお待ちください。")
                .setPositiveButton("OK") { _, which -> }
                .show()
            return
        }
        isProcess = true
        isActivity = !isActivity
        val mMap = myMap.getMyMap()

        CoroutineScope(Dispatchers.Main).launch {
            if (isActivity) {
                val location = gps.getCurrentLocation()
                val origin = LatLng(location.latitude, location.longitude)

                val places = place.pickCheckpoint(origin)
                route.drawRoute(origin, places)
            } else {
                mMap.clear()
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
            myMap.addMarker(point)
        } else {
            Toast.makeText(
                activity,
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
        // TODO: チェックポイントも消えてしまう
        // TODO: MarkerListクラスの修正
//        marker.remove()
    }
}