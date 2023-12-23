package com.example.lcdtest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.lcdtest.ui.theme.LCDTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LCDTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    setContentView(R.layout.main_menu)

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
