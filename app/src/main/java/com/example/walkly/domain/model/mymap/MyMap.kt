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
    private var markerList: MarkerList = MarkerList()

    /**
     * チェックポイントとなるマーカーを設置する
     *
     * @param point
     */
    fun addMarker(point: PointOfInterest) {
        if (!markerList.checkDuplicate(point.name)) {
            val markerOptions = MarkerOptions().position(point.latLng)
            markerOptions.title(point.name)
            val marker = mMap.addMarker(markerOptions)

            markerList.add(marker)
        }
    }

    /**
     * マーカーを追加する。リストに保存しないので上限はなし
     *
     * @param option
     */
    fun addMarker(option: MarkerOptions) {
        mMap.addMarker(option)
    }

    /**
     * マーカーの削除とリストからの削除
     *
     * @param marker
     */
    fun deleteMarker(marker: Marker) {
        markerList.delete(marker)
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
        markerList = MarkerList()
    }
}