package com.bingze.autoclickerdemo

data class ClickPoint(
    val id: Int,
    var xRatio: Float,
    var yRatio: Float,
    var delay: Long,
    var duration: Long
)