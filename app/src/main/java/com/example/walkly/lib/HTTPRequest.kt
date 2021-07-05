package com.example.walkly.lib

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.walkly.domain.model.MyApplication

class HTTPRequest {
    fun getRequest(
        url: String,
        listener: Response.Listener<String>,
        errorListener: Response.ErrorListener,
    ) {
        val request =
            object: StringRequest(Method.GET, url, listener, errorListener){}
        val requestQueue = Volley.newRequestQueue(MyApplication.getContext())
        requestQueue.add(request)
    }
}