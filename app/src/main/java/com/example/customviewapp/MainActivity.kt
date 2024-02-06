package com.example.customviewapp


import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide


class MainActivity : AppCompatActivity() {

    private var startDegree = 0F
    private var finishDegree = 0F
    private lateinit var rainbowWheel: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinButton = findViewById<Button>(R.id.spinBtn)
        val clearButton = findViewById<Button>(R.id.clearViewBtn)
        val imageView = findViewById<ImageView>(R.id.mainActivityImageView)
        rainbowWheel = findViewById(R.id.spinnerIV);

        spinButton.setOnClickListener {
            onClickSpin()
        }

        clearButton.setOnClickListener {
            imageView.setImageDrawable(null)
        }
    }

    private fun onClickSpin() {
        startDegree = finishDegree % 360
        finishDegree = ((0..3600).random() + 720).toFloat()
        val rotate = RotateAnimation(
            startDegree, finishDegree, RotateAnimation.RELATIVE_TO_SELF,
            0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f
        )
        rotate.duration = 3600
        rotate.fillAfter = true
        rotate.interpolator = DecelerateInterpolator()
        rotate.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                spinResult((360 - finishDegree % 360).toInt())
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        rainbowWheel.startAnimation(rotate)
    }

    private fun spinResult(degree: Int) {
        val imageUrl = getUrl()
        val imageView = findViewById<ImageView>(R.id.mainActivityImageView)

        when (degree) {
            in 310..360 -> drawText("Красный", imageView, Color.RED)
            in 258..309 -> loadImage(imageUrl, imageView)
            in 207..257 -> drawText("Жёлтый", imageView, Color.YELLOW)
            in 155..206 -> loadImage(imageUrl, imageView)
            in 103..154 -> drawText("Голубой", imageView, Color.BLUE)
            in 51..102 -> loadImage(imageUrl, imageView)
            in 0..50 -> drawText("Фиолетовый", imageView, ContextCompat.getColor(this, R.color.violet))
        }
    }

    private fun drawText(text: String, imageView: ImageView, textColor: Int) {

        val newImage = Bitmap.createBitmap(320, 200, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(newImage)

        val paint = Paint()
        paint.color = textColor
        paint.style = Paint.Style.FILL
        paint.textSize = 40F
        canvas.rotate(15F)
        canvas.drawText(text, 20F, 30F, paint)

        imageView.setImageBitmap(newImage)
    }

    private fun loadImage(url: String, imageView: ImageView) {
        Glide.with(this).load(url).into(imageView)
    }

    private fun getUrl(): String {
        val urlRandomizer = (0..999999).random()
        return "https://loremflickr.com/320/240?random=$urlRandomizer"
    }
}