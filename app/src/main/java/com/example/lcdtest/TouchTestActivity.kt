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
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.lcdtest.ui.theme.LCDTestTheme
import java.util.Locale

class TouchTestActivity : ComponentActivity() {

    private lateinit var fingerCountTextView: TextView
    private lateinit var countDownClock: TextView
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hideSystemUI()
        adjustLayoutForCutout()

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
                        this.getString(R.string.toast_touch_input_guide),
                        Toast.LENGTH_SHORT,
                    ).show()

                    val updatedContext = context.createConfigurationContext(configuration)
                    val rootView = LayoutInflater.from(updatedContext)
                        .inflate(R.layout.touch_test, null)

                    fingerCountTextView = rootView.findViewById(R.id.touch_input_num_indicator)
                    countDownClock = rootView.findViewById(R.id.countdown_text)

                    countDownTimer = object : CountDownTimer(30_000, 1_000) {
                        override fun onTick(millisUntilFinished: Long) {
                            val secondsRemaining = millisUntilFinished / 1_000
                            countDownClock.text = secondsRemaining.toString()
                        }

                        override fun onFinish() {
                            goToNextTest()
                        }
                    }
                    countDownTimer.start()

                    var fingerCount = 0
                    val touchTestLayout = rootView.findViewById<View>(R.id.touch_input_num_indicator)
                    touchTestLayout.setOnTouchListener { _, event ->
                        when (event.actionMasked) {
                            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                                // Handle touch events as needed
                                fingerCount++
                                fingerCountTextView.text = fingerCount.toString()
                            }
                            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                                // Handle touch events as needed
                                fingerCount--
                                fingerCountTextView.text = fingerCount.toString()
                            }
                        }
                        true
                    }

                    setContentView(rootView)
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

    private fun hideSystemUI() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                )
    }

    private fun goToNextTest() {
        val intent = Intent(this, TouchTest3Activity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }
}