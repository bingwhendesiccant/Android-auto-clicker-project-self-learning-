package com.bingze.autoclickerdemo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val clickPoints = mutableListOf<ClickPoint>()
    private val maxPoints = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val statusText = findViewById<TextView>(R.id.statusText)
        val addPointButton = findViewById<Button>(R.id.addPointButton)

        updateStatus(statusText)

        addPointButton.setOnClickListener {
            if (clickPoints.size >= maxPoints) {
                statusText.text = "已達上限，最多只能新增 $maxPoints 個點位"
                return@setOnClickListener
            }

            val newId = clickPoints.size + 1
            val newPoint = ClickPoint(
                id = newId,
                x = 100 * newId,
                y = 150 * newId,
                delay = 500,
                duration = 100
            )

            clickPoints.add(newPoint)
            updateStatus(statusText)
        }
    }

    private fun updateStatus(statusText: TextView) {
        statusText.text = "目前點位數：${clickPoints.size}"
    }
}