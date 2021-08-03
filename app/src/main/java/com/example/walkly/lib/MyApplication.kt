package com.example.walkly.lib

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.walkly.domain.model.mymap.MyMap

class MyApplication: Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var mContext: Context
        private lateinit var myMap: MyMap

        fun getContext(): Context {
            return mContext
        }

        fun setMap(mMap: MyMap) {
            myMap = mMap
        }

        fun getMap(): MyMap {
            return myMap
        }
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }
}