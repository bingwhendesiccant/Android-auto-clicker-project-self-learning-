package com.bingze.autoclickerdemo

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.view.accessibility.AccessibilityEvent
import android.accessibilityservice.AccessibilityService.GestureResultCallback
class AutoClickService : AccessibilityService() {

    companion object {
        var instance: AutoClickService? = null
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // 目前先不處理事件
    }

    override fun onInterrupt() {
        // 服務被系統中斷時會呼叫
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
    }

    override fun onDestroy() {
        super.onDestroy()
        if (instance == this) {
            instance = null
        }
    }

    fun performClick(
        x: Int,
        y: Int,
        duration: Long,
        onComplete: (() -> Unit)? = null
    ) {
        val path = Path().apply {
            moveTo(x.toFloat(), y.toFloat())
        }

        val gesture = GestureDescription.Builder()
            .addStroke(
                GestureDescription.StrokeDescription(
                    path,
                    0,
                    duration
                )
            )
            .build()

        dispatchGesture(
            gesture,
            object : GestureResultCallback() {
                override fun onCompleted(gestureDescription: GestureDescription?) {
                    super.onCompleted(gestureDescription)
                    onComplete?.invoke()
                }

                override fun onCancelled(gestureDescription: GestureDescription?) {
                    super.onCancelled(gestureDescription)
                    onComplete?.invoke()
                }
            },
            null
        )
    }
}