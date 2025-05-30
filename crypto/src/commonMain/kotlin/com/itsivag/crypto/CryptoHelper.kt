package com.itsivag.crypto

expect class CryptoHelper() {
    fun aesEncrypt(data: ByteArray): ByteArray
    fun aesDecrypt(encryptedData: ByteArray): ByteArray
}