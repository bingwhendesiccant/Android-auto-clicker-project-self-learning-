package com.bingze.autoclickerdemo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val clickPoints = mutableListOf<ClickPoint>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val statusText = findViewById<TextView>(R.id.statusText)
        val testButton = findViewById<Button>(R.id.testButton)

        clickPoints.add(ClickPoint(1, 300, 500, 1000, 100))
        clickPoints.add(ClickPoint(2, 600, 800, 500, 80))

        testButton.setOnClickListener {
            val totalPoints = clickPoints.size
            val firstPoint = clickPoints[0]

            statusText.text =
                "目前共有 $totalPoints 個點\n" +
                "第一點座標=(${firstPoint.x}, ${firstPoint.y})"
        }
    }
}