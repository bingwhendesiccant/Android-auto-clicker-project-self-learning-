package com.bingze.autoclickerdemo

data class ClickPoint(
    val id: Int,
    var x: Int,
    var y: Int,
    var delay: Long,
    var duration: Long
)