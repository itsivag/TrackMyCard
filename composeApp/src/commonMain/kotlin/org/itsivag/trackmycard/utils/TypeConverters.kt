package org.itsivag.trackmycard.utils

fun String.safeConvertToDouble(): Double {
    return try {
        this.toDouble()
    } catch (e: Exception) {
        0.0
    }
}