package com.example.lcdtest

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.lcdtest.ui.theme.LCDTestTheme

class RGBTestActivity : ComponentActivity() {
    private lateinit var imageView: ImageView
    private var currentImageIndex = 0
    private val imageArray = arrayOf(
        R.drawable.red_solid, R.drawable.green_solid, R.drawable.blue_solid,
        R.drawable.white_solid, R.drawable.black_solid,
        R.drawable.red_gradient, R.drawable.green_gradient, R.drawable.blue_gradient, R.drawable.monochrome_gradient,
        R.drawable.smpte_color
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI()
        adjustLayoutForCutout()
        setMaxScreenBrightness()
        setContent {
            LCDTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Toast.makeText(
                        baseContext,
                        this.getString(R.string.toast_rgb_guide),
                        Toast.LENGTH_SHORT,
                    ).show()

                    setContentView(R.layout.rgb_test)
                    imageView = findViewById(R.id.img_viewer)
                    imageView.setOnClickListener {
                        currentImageIndex++
                        if (currentImageIndex < imageArray.size) {
                            imageView.setImageResource(imageArray[currentImageIndex])
                        } else {
                            setResult(RESULT_OK)
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun adjustLayoutForCutout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val cutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes.layoutInDisplayCutoutMode = cutoutMode
        }
    }

    private fun setMaxScreenBrightness() {
        // Get the window attributes
        val attributes = window.attributes

        // Set the brightness to maximum (1.0f)
        attributes.screenBrightness = 1.0f

        // Apply the updated attributes to the window
        window.attributes = attributes
    }

    private fun hideSystemUI() {
        // Set the activity to full screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Hide the status bar and navigation bar
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                )
    }
}