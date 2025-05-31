package com.itsivag.crypto

expect class CryptoHelper() {
    fun aesEncrypt(data: ByteArray): ByteArray
    fun aesDecrypt(encryptedData: ByteArray): ByteArray
    inline fun <reified T : Any> T.encryptFields(): T
    inline fun <reified T : Any> T.decryptFields(cryptoHelper: CryptoHelper): T
}