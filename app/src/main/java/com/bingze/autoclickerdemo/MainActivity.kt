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
        val listText = findViewById<TextView>(R.id.listText)
        val removePointButton = findViewById<Button>(R.id.removePointButton)

        updateStatus(statusText, listText)

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
            updateStatus(statusText, listText)
        }

        removePointButton.setOnClickListener {

            if (clickPoints.isEmpty()) {
                statusText.text = "沒有點位可以刪除"
                return@setOnClickListener
            }

            clickPoints.removeAt(clickPoints.size - 1)

            updateStatus(statusText, listText)
        }
    }

    private fun updateStatus(statusText: TextView, listText: TextView) {
        statusText.text = "目前點位數：${clickPoints.size}"

        if (clickPoints.isEmpty()) {
            listText.text = "(尚無點位資料)"
            return
        }

        val builder = StringBuilder()

        for (point in clickPoints) {
            builder.append(
                "#${point.id} (${point.x}, ${point.y}) " +
                        "delay=${point.delay} duration=${point.duration}\n"
            )
        }

        listText.text = builder.toString()
    }
}