package com.example.walkly.domain.model

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import com.example.walkly.lib.Permission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.tasks.await

/**
 * GPS利用の許可を求めたり、現在地レイヤーを表示したりする
 */

class GPS(private val activity: AppCompatActivity) {
    private val permission: Permission = Permission()
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(activity)
    private lateinit var lastLocation: Location

    /**
     * 現在地レイヤーの表示
     *
     * @param mMap GoogleMap SDK のメインクラス
     */
    fun enableCurrentLocation(mMap: GoogleMap) {
        mMap.isMyLocationEnabled =
            permission.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        fusedLocationClient.lastLocation.addOnSuccessListener(activity) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 18f))
            }
        }
    }

    /**
     * 同期的に現在地を取得する
     */
    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Location {
        return fusedLocationClient.lastLocation.await()
    }

}