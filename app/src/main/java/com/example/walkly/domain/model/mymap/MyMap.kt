package com.example.walkly.domain.model.mymap

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.gms.maps.model.PolylineOptions

/**
 * 様々な処理に必要なGoogleMapクラスを管理している
 */
class MyMap(private val mMap: GoogleMap) {
    private var manualCheckPoint: ManualCheckPoint = ManualCheckPoint()

    /**
     * チェックポイントとなるマーカーを設置する
     *
     * @param point
     */
    fun addMarker(point: PointOfInterest) {
        val name = point.name
        if (!manualCheckPoint.checkDuplicate(name)) {
            val markerOptions = MarkerOptions().position(point.latLng)
            markerOptions.title(name)
            val marker = addMarker(markerOptions)

            if (marker != null) {
                manualCheckPoint.add(marker)
            }
        }
    }

    /**
     * マーカーを追加する。リストに保存しないので上限はなし
     *
     * @param option
     */
    fun addMarker(option: MarkerOptions): Marker? {
        return mMap.addMarker(option)
    }

    /**
     * マーカーの削除とリストからの削除
     *
     * @param marker
     */
    fun deleteMarker(marker: Marker) {
        manualCheckPoint.delete(marker)
    }

    /**
     * ポリラインの線画
     *
     * @param option
     */
    fun addPolyline(option: PolylineOptions) {
        mMap.addPolyline(option)
    }

    /**
     * マップの初期化
     */
    fun clear() {
        mMap.clear()
        manualCheckPoint = ManualCheckPoint()
    }
}