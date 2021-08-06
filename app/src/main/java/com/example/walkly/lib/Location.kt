package com.example.walkly.lib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

/**
 * ページ遷移を行うクラス
 */
class Location {

    /**
     * ページ遷移する
     *
     * @param packageContext 遷移元
     * @param cls 遷移先
     */
    fun push(packageContext: AppCompatActivity, cls: Class<*>) {
        val intent = Intent(packageContext, cls)
        packageContext.startActivity(intent)
        packageContext.finish()
    }
}