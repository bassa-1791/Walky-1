package com.example.walkly

import android.Manifest
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.example.walkly.application.MapApplicationService
import com.example.walkly.lib.Permission
import com.example.walkly.ui.MapCallback
import com.example.walkly.ui.PermissionCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MapsActivity : AppCompatActivity() {
    private val mapApplication: MapApplicationService = MapApplicationService(this)
    private val mapCallback: MapCallback = MapCallback(mapApplication)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(mapCallback)

        val activityButton = findViewById<FloatingActionButton>(R.id.fab)
        activityButton.setOnClickListener {
            // TODO: 拒否状態(false)→パーミッション拒否の場合、遷移する必要がない
            val permission = Permission()
            if (permission.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                mapApplication.handleActivityButton()
            } else {
                permission.requestPermission(
                    this@MapsActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    R.integer.location_request_code
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionCallback = PermissionCallback(this)
        if (requestCode == R.integer.location_request_code) {
            permissionCallback.onLocationResultCallback(this@MapsActivity)
        }
    }
}
