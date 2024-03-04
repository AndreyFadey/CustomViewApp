package com.example.customviewapp


import android.graphics.Color
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.customviewapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var startDegree = 0F
    private var finishDegree = 0F

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageView = binding.resultImageView
        val customView = binding.customView
        customView.scaleX = binding.scaleSeekBar.progress / 100F
        customView.scaleY = binding.scaleSeekBar.progress / 100F

        binding.spinBtn.setOnClickListener {
            onClickSpin()
        }

        binding.clearViewBtn.setOnClickListener {
            imageView.setImageDrawable(null)
        }
        binding.scaleSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onProgressChanged(
                seekBar: SeekBar, progress: Int,
                fromUser: Boolean
            ) {
                customView.scaleX = progress / 100F
                customView.scaleY = progress / 100F
            }
        })
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
        binding.customView.startAnimation(rotate)
    }

    private fun spinResult(degree: Int) {
        val imageUrl = getUrl()
        val imageView = findViewById<ImageView>(R.id.resultImageView)

        when (degree) {
            in 0..50 -> binding.customView.drawText(ContextCompat.getString(this, R.string.red), imageView, Color.RED)
            in 51..102 -> loadImage(imageUrl, imageView)
            in 103..154 -> binding.customView.drawText(ContextCompat.getString(this, R.string.yellow), imageView, Color.YELLOW)
            in 155..206 -> loadImage(imageUrl, imageView)
            in 207..257 -> binding.customView.drawText(ContextCompat.getString(this, R.string.light_blue), imageView, Color.BLUE)
            in 258..309 -> loadImage(imageUrl, imageView)
            in 310..360 -> binding.customView.drawText(
                ContextCompat.getString(this, R.string.violet),
                imageView,
                ContextCompat.getColor(this, R.color.violet)
            )
        }
    }

    private fun loadImage(url: String, imageView: ImageView) {
        Glide.with(this).load(url).into(imageView)
    }

    private fun getUrl(): String {
        val urlRandomizer = (0..999999).random()
        return "https://loremflickr.com/320/240?random=$urlRandomizer"
    }
}