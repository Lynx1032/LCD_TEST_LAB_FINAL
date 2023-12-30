package com.example.lcdtest

import android.app.Activity
import android.util.DisplayMetrics

object DisplayMetricsHelper {
    fun getScreenWidth(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    fun getScreenHeight(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    fun getScreenSize(activity: Activity): Double {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        // Tính toán kích thước màn hình dựa trên thông tin DPI
        val xDpi = displayMetrics.xdpi.toDouble()
        val yDpi = displayMetrics.ydpi.toDouble()

        // Tính toán độ dài đường chéo của màn hình (inch)
        val screenInches = Math.sqrt(Math.pow(displayMetrics.widthPixels.toDouble() / xDpi, 2.0) +
                Math.pow(displayMetrics.heightPixels.toDouble() / yDpi, 2.0))
        // Làm tròn kết quả đến số thập phân thứ nhất
        val roundedScreenInches = String.format("%.1f", screenInches).replace(",", ".").toDouble()
        return roundedScreenInches

    }
}