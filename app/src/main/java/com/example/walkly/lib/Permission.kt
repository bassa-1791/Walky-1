package com.example.walkly.lib

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.walkly.domain.model.MyApplication

/**
 * パーミッションに関する機能をまとめるクラス
 *
 * @property activity メインアクティビティ
 */
class Permission(private val activity: AppCompatActivity) {

    /**
     * パーミッション権限の確認
     *
     * @param permission Manifest.permission.XXX
     * @return 許可されていたらtrue
     */
    fun checkPermission(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(
            MyApplication.getContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * パーミッション権限を求める
     *
     * @param permission Manifest.permission.XXX
     * @param request_code res/permission.xml内の数字
     */
    fun requestPermission(permission: String, request_code: Int) {
        // TODO: 第一引数のactivityを変更して、クラスの引数を取り除きたい
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(permission),
            request_code
        )
    }
}