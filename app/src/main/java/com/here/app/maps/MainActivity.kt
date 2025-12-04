package com.here.app.maps

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.urlText)
        val data: Uri? = intent?.data

        textView.text = if (data != null) {
            "URL z Woltu:\n${data}\n\nlat/lon část:\n${data.path}"
        } else {
            "Appka spuštěná normálně (bez deeplinku)"
        }
    }
}
