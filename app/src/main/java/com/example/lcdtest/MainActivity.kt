package com.example.lcdtest

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.lcdtest.ui.theme.LCDTestTheme
import java.util.Locale

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val selectedLanguage = sharedPreferences.getString("selectedLanguage", "en_us")

        val locale = Locale(selectedLanguage)
        Locale.setDefault(locale)

        val resources = resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        val context = createConfigurationContext(configuration)
        resources.updateConfiguration(configuration, resources.displayMetrics)

        setContent {
            LCDTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val updatedContext = context.createConfigurationContext(configuration)
                    setContentView(R.layout.main_menu)

                    val settingsButton = findViewById<ImageButton>(R.id.settings_btn)
                    settingsButton.setOnClickListener {
                        val intent = Intent(this, SettingsActivity::class.java)
                        startActivity(intent)
                    }

                    val sysInfoButton = findViewById<Button>(R.id.sys_info_btn)
                    sysInfoButton.setOnClickListener {
                        val intent = Intent(this, SysInfoActivity::class.java)
                        startActivity(intent)
                    }

                    val rgbTestButton = findViewById<Button>(R.id.rgb_test_btn)
                    rgbTestButton.setOnClickListener {
                        val intent = Intent(this, RGBTestActivity::class.java)
                        startActivity(intent)
                    }

                    val touchTestButton = findViewById<Button>(R.id.touch_test_btn)
                    touchTestButton.setOnClickListener {
                        val intent = Intent(this, TouchTestActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}
