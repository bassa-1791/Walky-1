package com.example.walkly.ui

import android.Manifest
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.walkly.MapsActivity
import com.example.walkly.lib.Location
import com.example.walkly.lib.Permission

/**
 * パーミッション権限を求めた結果、実行されるコールバックメソッドをまとめたクラス
 *
 * @property activity メインアクティビティ
 * @property permission パーミッション権限の確認を行うPermissionクラス
 */
class PermissionCallback(private val activity: AppCompatActivity) {
    private val permission: Permission = Permission()

    /**
     * GPSの権限を拒否されたら警告文を表示
     *
     * @param packageContext 遷移元
     */
    fun onLocationResultCallback(packageContext: AppCompatActivity) {
        val location = Location()
        if (!permission.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            AlertDialog.Builder(activity)
                .setTitle("警告")
                .setMessage("一部機能が利用できません")
                .setPositiveButton("OK") { _, _ -> }
                .show()
        } else {
            location.push(packageContext, MapsActivity::class.java)
        }
    }
}