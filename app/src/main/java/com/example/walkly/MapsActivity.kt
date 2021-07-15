package com.example.walkly

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.example.walkly.application.MapApplicationService
import com.example.walkly.ui.MapCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity() {



    private val mapApplication: MapApplicationService = MapApplicationService(this)
    private val mapCallback: MapCallback = MapCallback(mapApplication)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(mapCallback)

        // TODO: Context置き換え
        val button = findViewById<FloatingActionButton>(R.id.fab)
        button.setOnClickListener {
            mapApplication.handleActivityButton()
        }


//        Picasso.get()
//            //いらすとやの画像URL
//            .load("https://1.bp.blogspot.com/-kwMHBpDRC98/WMfCOCDhmCI/AAAAAAABClk/0YhKPlx69H8akEluJniMmVV-RoJCRtPvACLcB/s800/onsei_ninshiki_smartphone.png")
//            .resize(300, 300) //表示サイズ指定
//            .centerCrop() //resizeで指定した範囲になるよう中央から切り出し
//            .into(imageView) //imageViewに流し込み

    }


}