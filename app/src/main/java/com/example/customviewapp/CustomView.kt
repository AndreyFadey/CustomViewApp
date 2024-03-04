package com.example.customviewapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat

class CustomView(
    context: Context,
    attributeSet: AttributeSet
) : View(context, attributeSet) {
    private val paint = Paint()
    private val startAngle = 0f
    private val colors = listOf(
        ContextCompat.getColor(context, R.color.red),
        ContextCompat.getColor(context, R.color.orange),
        ContextCompat.getColor(context, R.color.yellow),
        ContextCompat.getColor(context, R.color.green),
        ContextCompat.getColor(context, R.color.lightBlue),
        ContextCompat.getColor(context, R.color.blue),
        ContextCompat.getColor(context, R.color.violet)
    )
    private val sweepAngle = 360f / colors.size

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawWheel(canvas)
    }

    private fun drawWheel(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = width / 2f
        val radius = width.coerceAtMost(height) / 2f

        for (i in colors.indices) {
            paint.color = colors[i]
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                startAngle + i * sweepAngle,
                sweepAngle,
                true,
                paint
            )
        }
    }
    fun drawText(text: String, imageView: ImageView, textColor: Int) {

        val newImage = Bitmap.createBitmap(300, 200, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(newImage)
        val paint = Paint()
        paint.color = textColor
        paint.style = Paint.Style.FILL
        paint.textSize = 40F
        canvas.drawText(text, 20F, 30F, paint)

        imageView.setImageBitmap(newImage)
    }
}