package com.here.app.maps

import android.content.Context
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)

        val switchInstant = findViewById<Switch>(R.id.switchInstant)
        val switchNavigate = findViewById<Switch>(R.id.switchNavigate)

        // načíst uložené hodnoty
        switchInstant.isChecked = prefs.getBoolean("instantOpen", false)
        switchNavigate.isChecked = prefs.getBoolean("autoNavigate", false)

        // uložit změny
        switchInstant.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("instantOpen", isChecked).apply()
        }

        switchNavigate.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("autoNavigate", isChecked).apply()
        }
    }
}
