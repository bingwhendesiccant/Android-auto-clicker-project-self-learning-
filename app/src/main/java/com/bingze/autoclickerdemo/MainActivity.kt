package com.bingze.autoclickerdemo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import android.content.Intent
import android.provider.Settings
import android.text.TextUtils
import android.accessibilityservice.GestureDescription
import android.graphics.Path

class MainActivity : AppCompatActivity() {

    private val clickPoints = mutableListOf<ClickPoint>()
    private val maxPoints = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val statusText = findViewById<TextView>(R.id.statusText)
        val listText = findViewById<TextView>(R.id.listText)
        val addPointButton = findViewById<Button>(R.id.addPointButton)
        val removePointButton = findViewById<Button>(R.id.removePointButton)

        val editButtonUp = findViewById<Button>(R.id.EditButtonUp)
        val editButtonDown = findViewById<Button>(R.id.EditButtonDown)
        val chooseText = findViewById<TextView>(R.id.ChooseButton)
        val editXRatioInput = findViewById<EditText>(R.id.editXRatioInput)
        val editYRatioInput = findViewById<EditText>(R.id.editYRatioInput)
        val editDelayInput = findViewById<EditText>(R.id.editDelayInput)
        val editDurationInput = findViewById<EditText>(R.id.editDurationInput)
        val applyEditButton = findViewById<Button>(R.id.applyEditButton)

        val savePointsButton = findViewById<Button>(R.id.savePointsButton)
        val loadPointsButton = findViewById<Button>(R.id.loadPointsButton)

        val openAccessibilitySettingsButton =
            findViewById<Button>(R.id.openAccessibilitySettingsButton)

        val expectedServiceName =
            "$packageName/${AutoClickService::class.java.name}"
        val accessibilityStatusText =
            findViewById<TextView>(R.id.accessibilityStatusText)
        val checkAccessibilityButton =
            findViewById<Button>(R.id.checkAccessibilityButton)

        val testClickSelectedButton =
            findViewById<Button>(R.id.testClickSelectedButton)

        loadClickPoints()
        updateStatus(statusText, listText)
        updateSelectedPoint(chooseText)
        //load preset points from memory
        updateAccessibilityStatus(accessibilityStatusText)
        //ensure get access while open app

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
            selectedPointIndex = clickPoints.size - 1
            updateStatus(statusText, listText)
            updateSelectedPoint(chooseText)
        }

        removePointButton.setOnClickListener {

            if (clickPoints.isEmpty()) {
                statusText.text = "沒有點位可以刪除"
                return@setOnClickListener
            }

            clickPoints.removeAt(clickPoints.size - 1)

            if (selectedPointIndex >= clickPoints.size) {
                selectedPointIndex = clickPoints.size - 1
            }

            updateStatus(statusText, listText)
            updateSelectedPoint(chooseText)

        }

        editButtonUp.setOnClickListener {
            if (clickPoints.isEmpty()) {
                statusText.text = "目前沒有點位可以選擇"
                updateSelectedPoint(chooseText)
                return@setOnClickListener
            }

            selectedPointIndex--

            if (selectedPointIndex < 0) {
                selectedPointIndex = clickPoints.size - 1
            }

            updateSelectedPoint(chooseText)
        }

        editButtonDown.setOnClickListener {
            if (clickPoints.isEmpty()) {
                statusText.text = "目前沒有點位可以選擇"
                updateSelectedPoint(chooseText)
                return@setOnClickListener
            }

            selectedPointIndex++

            if (selectedPointIndex >= clickPoints.size) {
                selectedPointIndex = 0
            }

            updateSelectedPoint(chooseText)
        }

        applyEditButton.setOnClickListener {
            if (clickPoints.isEmpty()) {
                statusText.text = "目前沒有點位可以修改"
                updateSelectedPoint(chooseText)
                return@setOnClickListener
            }

            val newXRatio = editXRatioInput.text.toString().toFloatOrNull()
            val newYRatio = editYRatioInput.text.toString().toFloatOrNull()
            val newDelay = editDelayInput.text.toString().toLongOrNull()
            val newDuration = editDurationInput.text.toString().toLongOrNull()

            if (newXRatio == null || newYRatio == null || newDelay == null || newDuration == null) {
                statusText.text = "請完整輸入有效數值"
                return@setOnClickListener
            }

            if (newXRatio < 0f || newXRatio > 1f || newYRatio < 0f || newYRatio > 1f) {
                statusText.text = "xRatio / yRatio 必須介於 0 到 1 之間"
                return@setOnClickListener
            }

            if (newDelay < 0 || newDuration <= 0) {
                statusText.text = "delay 不可小於 0，duration 必須大於 0"
                return@setOnClickListener
            }

            val oldPoint = clickPoints[selectedPointIndex]

            clickPoints[selectedPointIndex] = oldPoint.copy(
                xRatio = newXRatio,
                yRatio = newYRatio,
                delay = newDelay,
                duration = newDuration
            )

            statusText.text = "已修改 ID=${oldPoint.id} 的點位"
            updateStatus(statusText, listText)
            updateSelectedPoint(chooseText)
        }

        savePointsButton.setOnClickListener {
            saveClickPoints()
            statusText.text = "已儲存目前點位設定"
        }

        loadPointsButton.setOnClickListener {
            loadClickPoints()

            if (selectedPointIndex >= clickPoints.size) {
                selectedPointIndex = clickPoints.size - 1
            }

            if (selectedPointIndex < 0) {
                selectedPointIndex = 0
            }

            updateStatus(statusText, listText)
            updateSelectedPoint(chooseText)
            statusText.text = "已讀取點位設定"
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

            if (selectedPointIndex >= clickPoints.size) {
                selectedPointIndex = clickPoints.size - 1
            }

            statusText.text = "已刪除 ID=$targetId 的點位"
            removeIdInput.text.clear()
            updateStatus(statusText, listText)
            updateSelectedPoint(chooseText)
        }

        openAccessibilitySettingsButton.setOnClickListener {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        }

        checkAccessibilityButton.setOnClickListener {
            updateAccessibilityStatus(accessibilityStatusText)
        }

        testClickSelectedButton.setOnClickListener {
            if (!isAccessibilityServiceEnabled()) {
                statusText.text = "請先啟用無障礙權限"
                return@setOnClickListener
            }

            if (clickPoints.isEmpty()) {
                statusText.text = "目前沒有點位可以測試"
                return@setOnClickListener
            }

            val service = AutoClickService.instance

            if (service == null) {
                statusText.text = "無障礙服務尚未連線，請重新開啟服務或重啟 App"
                return@setOnClickListener
            }

            val point = clickPoints[selectedPointIndex]

            val metrics = resources.displayMetrics
            val width = metrics.widthPixels
            val height = metrics.heightPixels

            val realX = (point.xRatio * width).toInt()
            val realY = (point.yRatio * height).toInt()

            service.performClick(realX, realY, point.duration)

            statusText.text = "已測試點擊 ID=${point.id}：($realX, $realY)"
        }

    }
    private var selectedPointIndex = 0

    private fun updateStatus(statusText: TextView, listText: TextView) {
        statusText.text = "目前點位數：${clickPoints.size}"

        if (clickPoints.isEmpty()) {
            listText.text = "(尚無點位資料)"
            return
        }

        val metrics = resources.displayMetrics
        val width = metrics.widthPixels
        val height = metrics.heightPixels

        val builder = StringBuilder()

        for (point in clickPoints) {

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
    private fun updateSelectedPoint(chooseText: TextView) {
        if (clickPoints.isEmpty()) {
            chooseText.text = "目前選定：無"
            selectedPointIndex = 0
            return
        }

        if (selectedPointIndex >= clickPoints.size) {
            selectedPointIndex = clickPoints.size - 1
        }

        if (selectedPointIndex < 0) {
            selectedPointIndex = 0
        }

        val point = clickPoints[selectedPointIndex]

        chooseText.text =
            "目前選定：#${point.id}\n" +
                    "ratio=(${"%.2f".format(point.xRatio)}, ${"%.2f".format(point.yRatio)})\n" +
                    "delay=${point.delay} duration=${point.duration}"
    }
    private fun saveClickPoints() {
        val jsonArray = JSONArray()

        for (point in clickPoints) {
            val jsonObject = JSONObject()

            jsonObject.put("id", point.id)
            jsonObject.put("xRatio", point.xRatio)
            jsonObject.put("yRatio", point.yRatio)
            jsonObject.put("delay", point.delay)
            jsonObject.put("duration", point.duration)

            jsonArray.put(jsonObject)
        }

        val sharedPreferences = getSharedPreferences("click_point_storage", MODE_PRIVATE)

        sharedPreferences.edit()
            .putString("click_points", jsonArray.toString())
            .apply()
    }
    private fun loadClickPoints() {
        val sharedPreferences = getSharedPreferences("click_point_storage", MODE_PRIVATE)
        val jsonString = sharedPreferences.getString("click_points", null)

        if (jsonString == null) {
            clickPoints.clear()
            selectedPointIndex = 0
            return
        }

        val jsonArray = JSONArray(jsonString)

        clickPoints.clear()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)

            val point = ClickPoint(
                id = jsonObject.getInt("id"),
                xRatio = jsonObject.getDouble("xRatio").toFloat(),
                yRatio = jsonObject.getDouble("yRatio").toFloat(),
                delay = jsonObject.getLong("delay"),
                duration = jsonObject.getLong("duration")
            )

            clickPoints.add(point)
        }

        reorderIds()
        selectedPointIndex = 0
    }
    private fun isAccessibilityServiceEnabled(): Boolean {
        val expectedServiceName =
            "$packageName/${AutoClickService::class.java.name}"

        val enabledServices = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        ) ?: return false

        val splitter = TextUtils.SimpleStringSplitter(':')
        splitter.setString(enabledServices)

        while (splitter.hasNext()) {
            val serviceName = splitter.next()

            if (serviceName.equals(expectedServiceName, ignoreCase = true)) {
                return true
            }
        }

        return false
    }
    private fun updateAccessibilityStatus(statusText: TextView) {
        if (isAccessibilityServiceEnabled()) {
            statusText.text = "無障礙權限：已啟用"
        } else {
            statusText.text = "無障礙權限：未啟用"
        }
    }
}