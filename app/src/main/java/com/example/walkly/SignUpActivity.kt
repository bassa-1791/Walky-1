package com.example.walkly

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val button: Button = findViewById<Button>(R.id.Activity_Button)
        button.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        AlertDialog.Builder(this)
            .setTitle("taitor")
            .setMessage("message")
            .setPositiveButton("OK", {dialog, which ->})
            .show()
    }

}