package com.example.lcdtest

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.lcdtest.ui.theme.LCDTestTheme
import java.util.Locale

class SysInfoActivity : ComponentActivity() {
    lateinit var widthTextView: TextView
    lateinit var heightTextView: TextView
    lateinit var sizeTextView: TextView

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
                    setContentView(R.layout.sys_info)

                    widthTextView = findViewById(R.id.tv_scr_Width)
                    heightTextView = findViewById(R.id.tv_scr_Height)
                    sizeTextView = findViewById(R.id.tv_scr_Size)

                    val metricsButton: Button = findViewById(R.id.btn_dis_Mertric)
                    metricsButton.setOnClickListener{

                        // Clear previous text to prevent the same button is pressed accidentally
                        widthTextView.text = this.getString(R.string.screen_width)
                        heightTextView.text = this.getString(R.string.screen_height)
                        sizeTextView.text = this.getString(R.string.screen_size)

                        widthTextView.text = widthTextView.text.toString().plus(DisplayMetricsHelper.getScreenWidth(this).toString()).plus(" Pixel")
                        heightTextView.text = heightTextView.text.toString().plus(DisplayMetricsHelper.getScreenHeight(this).toString()).plus(" Pixel")
                        sizeTextView.text = sizeTextView.text.toString().plus(DisplayMetricsHelper.getScreenSize(this).toString()).plus(" Inches")
                    }
                }
            }
        }
    }
}