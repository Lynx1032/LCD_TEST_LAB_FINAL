package com.example.lcdtest

import android.app.Activity
import android.graphics.Rect
import android.util.DisplayMetrics
import kotlin.math.pow
import kotlin.math.sqrt

object DisplayMetricsHelper {

    fun getScreenWidth(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    // Get the screen height including system bars
    fun getScreenHeight(activity: Activity): Int {
        val window = activity.window
        val rootView = window.decorView.rootView

        val visibleRect = Rect()
        rootView.getWindowVisibleDisplayFrame(visibleRect)

        val screenHeight: Int = visibleRect.height()    // Visible height without system bars

        val decorViewHeight = rootView.height    // Height of the entire window, including system bars

        // Calculate the height of system bars by subtracting the visible height from the entire height
        val systemBarHeight = decorViewHeight - screenHeight

        // Add the system bar height to the visible height to get the screen height including system bars
        val screenWithSystemBarsHeight = screenHeight + systemBarHeight

        return screenWithSystemBarsHeight
    }

    // Get the screen diagonal including system bars
    fun getScreenSize(activity: Activity): Double {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)

        val screenWidth: Int = getScreenWidth(activity) // Get the screen width
        val screenWidthDPI: Float = displayMetrics.xdpi // Get the xdpi using DisplayMetrics
        val screenHeight: Int = getScreenHeight(activity) // Get the screen height (include system UI bars height if present)
        val screenHeightDPI: Float = displayMetrics.ydpi // Get the ydpi using DisplayMetrics

        val screenSizeX: Double = screenWidth.toDouble() / screenWidthDPI.toDouble() // Calculate the physical width size in inches
        val screenSizeY: Double = screenHeight.toDouble() / screenHeightDPI.toDouble() // Calculate the physical height size in inches

        val screenDiagonal: Double = sqrt(screenSizeX.pow(2.0) + screenSizeY.pow(2.0)) // Pythagoras theorem

        return String.format("%.1f", screenDiagonal).replace(",", ".").toDouble() // Round the result to 1 decimal place
    }
}