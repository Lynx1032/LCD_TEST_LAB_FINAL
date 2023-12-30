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
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class CustomDrawingView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val path = Path()
    private val paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> path.moveTo(x, y)
            MotionEvent.ACTION_MOVE -> path.lineTo(x, y)
            MotionEvent.ACTION_UP -> {
                path.lineTo(x, y)
                path.reset()
            }
        }

        invalidate()
        return true
    }
}