package com.djdevs.djnejk.testdeeplink

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

        if (data != null) {
            textView.text = "URL:\n${data}"
        } else {
            textView.text = "Appka spuštěná normálně (bez deeplinku)"
        }
    }
}
