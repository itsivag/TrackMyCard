package com.itsivag.crypto

expect class CryptoHelper() {
    fun aesEncrypt(data: ByteArray): ByteArray
    fun aesDecrypt(encryptedData: ByteArray): ByteArray
    inline fun <reified T : Any> T.encryptFields(vararg exclusions : String): T
    inline fun <reified T : Any> T.decryptFields(vararg exclusions: String): T
}