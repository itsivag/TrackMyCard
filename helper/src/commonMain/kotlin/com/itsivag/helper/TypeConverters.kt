package com.itsivag.helper

fun String.safeConvertToDouble(): Double {
    return try {
        this.toDouble()
    } catch (e: Exception) {
        0.0
    }
}

fun String.safeConvertToInt(): Int {
    return try {
        this.toInt()
    } catch (e: Exception) {
        0
    }
}