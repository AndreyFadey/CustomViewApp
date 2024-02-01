package com.example.customviewapp


import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import kotlin.math.abs


class MainActivity : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var handler: Handler
    private lateinit var imageList: ArrayList<Int>
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinButton = findViewById<Button>(R.id.spinBtn)
        val clearButton = findViewById<Button>(R.id.clearViewBtn)
        val imageView = findViewById<ImageView>(R.id.mainActivityImageView)

        init()
        setUpTransformer()

        spinButton.setOnClickListener {
            val randomInt = (0..6).random()
            val url = getUrl()

            handler.post(runnable(randomInt))

            when (randomInt) {
                0 -> drawText("Красный", imageView, Color.RED)
                1 -> loadImage(url, imageView)
                2 -> drawText("Жёлтый", imageView, Color.YELLOW)
                3 -> loadImage(url, imageView)
                4 -> drawText("Голубой", imageView, Color.BLUE)
                5 -> loadImage(url, imageView)
                6 -> drawText("Фиолетовый", imageView, R.color.violet)
                else -> return@setOnClickListener
            }

        }

        clearButton.setOnClickListener {
            imageView.setImageDrawable(null)
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

    private fun runnable(item: Int) = Runnable {
        viewPager2.currentItem = item
        println(viewPager2.currentItem)
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        viewPager2.setPageTransformer(transformer)
    }

    private fun init() {
        viewPager2 = findViewById(R.id.viewPager2)
        handler = Handler(Looper.myLooper()!!)

        imageList = arrayListOf(
            R.drawable.red_square,
            R.drawable.orange_square,
            R.drawable.yellow_square,
            R.drawable.green_square,
            R.drawable.light_blue_squad,
            R.drawable.blue_square,
            R.drawable.purple_square
        )

        adapter = Adapter(imageList, viewPager2)

        viewPager2.adapter = adapter
        viewPager2.currentItem = 3
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

    }
}