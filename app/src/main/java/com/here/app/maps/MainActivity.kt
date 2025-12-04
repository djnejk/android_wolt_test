package com.here.app.maps

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var lat: Double? = null
    private var lon: Double? = null
    private var mapyUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)

        val switchInstant = findViewById<Switch>(R.id.switchInstant)
        val switchNavigate = findViewById<Switch>(R.id.switchNavigate)

        val urlText = findViewById<TextView>(R.id.urlText)
        val coordsText = findViewById<TextView>(R.id.coordsText)
        val mapyUrlText = findViewById<TextView>(R.id.mapyUrlText)
        val openMapyButton = findViewById<Button>(R.id.openMapyButton)

        // --------- načtení uloženého nastavení ----------
        var instantOpen = prefs.getBoolean("instantOpen", false)
        var autoNavigate = prefs.getBoolean("autoNavigate", false)

        switchInstant.isChecked = instantOpen
        switchNavigate.isChecked = autoNavigate

        // uložení při změně
        switchInstant.setOnCheckedChangeListener { _, isChecked ->
            instantOpen = isChecked
            prefs.edit().putBoolean("instantOpen", isChecked).apply()
        }

        switchNavigate.setOnCheckedChangeListener { _, isChecked ->
            autoNavigate = isChecked
            prefs.edit().putBoolean("autoNavigate", isChecked).apply()
        }

        // --------- zpracování deeplinku ----------
        val data: Uri? = intent?.data

        if (data == null) {
            // appka spuštěná bez odkazu → jen nastavení
            urlText.text = "Aplikace spuštěná bez odkazu."
            coordsText.text = ""
            mapyUrlText.text = ""
            openMapyButton.isEnabled = false
            return
        }

        urlText.text = "Přijatá URL:\n$data"

        val segments = data.pathSegments
        val lastSeg = segments.lastOrNull()

        if (!lastSeg.isNullOrEmpty() && lastSeg.contains(",")) {
            val parts = lastSeg.split(",")
            if (parts.size == 2) {
                lat = parts[0].toDoubleOrNull()
                lon = parts[1].toDoubleOrNull()
            }
        }

        if (lat == null || lon == null) {
            coordsText.text = "Souřadnice se nepodařilo načíst."
            mapyUrlText.text = "—"
            openMapyButton.isEnabled = false
            return
        }

        val latVal = lat!!
        val lonVal = lon!!

        coordsText.text = "Souřadnice:\nlat = $latVal\nlon = $lonVal"

        val navigateFlag = if (autoNavigate) "true" else "false"

        mapyUrl =
            "https://mapy.com/fnc/v1/route" +
                    "?mapset=traffic" +
                    "&end=$lonVal,$latVal" +
                    "&routeType=car_fast_traffic" +
                    "&navigate=$navigateFlag"

        mapyUrlText.text = "Mapy.com URL:\n$mapyUrl"
        openMapyButton.isEnabled = true

        // instantní otevření podle nastavení
        if (instantOpen && mapyUrl != null) {
            playDing()
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mapyUrl)))
            finish()
            return
        }

        // ruční otevření tlačítkem
        openMapyButton.setOnClickListener {
            if (mapyUrl != null) {
                playDing()
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mapyUrl)))
            }
        }
    }

    private fun playDing() {
        val mp = MediaPlayer.create(this, R.raw.ding)
        mp.start()
    }
}
