package com.example.walkly

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.walkly.lib.Location
import com.example.walkly.lib.Permission
import com.example.walkly.ui.PermissionCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private val permission: Permission = Permission()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            if (permission.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                val location = Location()
                location.push(this@SplashActivity, MapsActivity::class.java)
            } else {
                permission.requestPermission(
                    this@SplashActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    R.integer.location_request_code
                )
            }
        }
    }

    /**
     * リクエストコードがR.integer.location_request_codeなら、GPSパーミッションのコールバックメソッドを実行する
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionCallback = PermissionCallback(this)
        if (requestCode == R.integer.location_request_code) {
            permissionCallback.onLocationResultCallback(this@SplashActivity)
        }
    }
}