package com.bingze.autoclickerdemo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.EditText
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
                xRatio = 0.1f * newId,
                yRatio = 0.1f * newId,
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


        val removeIdInput = findViewById<EditText>(R.id.removeIdInput)
        val removeByIdButton = findViewById<Button>(R.id.removeByIdButton)

        removeByIdButton.setOnClickListener {
            val inputText = removeIdInput.text.toString()

            if (inputText.isBlank()) {
                statusText.text = "請先輸入要刪除的點位 ID"
                return@setOnClickListener
            }

            val targetId = inputText.toIntOrNull()

            if (targetId == null) {
                statusText.text = "請輸入有效的數字 ID"
                return@setOnClickListener
            }

            val index = clickPoints.indexOfFirst { it.id == targetId }

            if (index == -1) {
                statusText.text = "找不到 ID=$targetId 的點位"
                return@setOnClickListener
            }

            clickPoints.removeAt(index)
            reorderIds()

            statusText.text = "已刪除 ID=$targetId 的點位"
            removeIdInput.text.clear()
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

            val metrics = resources.displayMetrics
            val width = metrics.widthPixels
            val height = metrics.heightPixels
            val realX = (point.xRatio * width).toInt()
            val realY = (point.yRatio * height).toInt()

            builder.append(
                "#${point.id} " +
                        "(ratio=${"%.2f".format(point.xRatio)}, ${"%.2f".format(point.yRatio)}) " +
                        "→ ($realX, $realY) " +
                        "delay=${point.delay} duration=${point.duration}\n"
            )
        }

        listText.text = builder.toString()
    }
    private fun reorderIds() {
        for (i in clickPoints.indices) {
            clickPoints[i] = clickPoints[i].copy(id = i + 1)
        }
    }
}