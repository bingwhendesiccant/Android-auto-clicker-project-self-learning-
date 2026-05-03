package com.bingze.autoclickerdemo

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class AutoClickService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // 目前先不處理事件
    }

    override fun onInterrupt() {
        // 服務被系統中斷時會呼叫
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        // 服務成功啟用時會呼叫
    }
}