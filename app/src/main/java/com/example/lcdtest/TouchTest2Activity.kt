/*
Đồ án TH Lập trình di động
GVHD: ThS Nguyễn Thái Công Nghĩa

Nhóm 2A:
    - Hồ Hoàng Nghiệp - 20200277
    - Nguyễn Tiến Trung - 20200382

Ứng dụng Test màn hình điện thoại
 */

package com.example.lcdtest

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.lcdtest.ui.theme.LCDTestTheme
import java.util.Locale
import kotlin.random.Random

class TouchTest2Activity : ComponentActivity() {

    //Variable for testing activity
    private lateinit var frameLayout: FrameLayout
    private var squareCount = 0 //Init squares number
    private var tappedCount = 0 //Number of touched squares
    private val squareSize = 75 //Size of the square edges

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //hideSystemUI()
        //adjustLayoutForCutout()

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
                    Toast.makeText(
                        baseContext,
                        this.getString(R.string.toast_touch_input_guide_2),
                        Toast.LENGTH_SHORT,
                    ).show()

                    val updatedContext = context.createConfigurationContext(configuration)
                    setContentView(R.layout.touch_test_2)

                    frameLayout = findViewById(R.id.frame_layout_main)

                    frameLayout.post {

                        // Number of squares that will appear on screen
                        squareCount = Random.nextInt(10, 30)

                        // Create and add random squares to the layout
                        repeat(squareCount) {
                            val square = View(this)
                            square.setBackgroundColor(Color.RED)
                            val layoutParams = FrameLayout.LayoutParams(squareSize, squareSize)
                            val newPosition = getRandomPosition()
                            layoutParams.leftMargin = newPosition.first
                            layoutParams.topMargin = newPosition.second
                            square.layoutParams = layoutParams

                            square.setOnClickListener {
                                onSquareTapped(it)
                            }

                            frameLayout.addView(square)
                        }
                    }
                }
            }
        }
    }
    private fun getRandomPosition(): Pair<Int, Int> {
        val maxWidth = frameLayout.width - squareSize
        val maxHeight = frameLayout.height - squareSize
        var leftMargin: Int
        var topMargin: Int

        do {
            leftMargin = Random.nextInt(maxWidth)
            topMargin = Random.nextInt(maxHeight)
        } while (isOverlap(leftMargin, topMargin))

        return Pair(leftMargin, topMargin)
    }

    private fun isOverlap(leftMargin: Int, topMargin: Int): Boolean {
        for (i in 0 until frameLayout.childCount) {
            val existingSquare = frameLayout.getChildAt(i)
            val existingParams = existingSquare.layoutParams as FrameLayout.LayoutParams

            if (leftMargin < existingParams.leftMargin + squareSize &&
                leftMargin + squareSize > existingParams.leftMargin &&
                topMargin < existingParams.topMargin + squareSize &&
                topMargin + squareSize > existingParams.topMargin
            ) {
                return true
            }
        }
        return false
    }
    private fun onSquareTapped(square: View) {
        square.setBackgroundColor(Color.GREEN)
        square.isClickable = false
        tappedCount++

        if (tappedCount == squareCount) {
            val intent = Intent(this, TouchTestActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun adjustLayoutForCutout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val cutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes.layoutInDisplayCutoutMode = cutoutMode
        }
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