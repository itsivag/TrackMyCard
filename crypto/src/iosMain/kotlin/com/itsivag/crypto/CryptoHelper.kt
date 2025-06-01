package com.itsivag.crypto

actual class CryptoHelper {
    actual fun aesEncrypt(data: ByteArray): ByteArray {
        return data
    }

    actual fun aesDecrypt(encryptedData: ByteArray): ByteArray {
        return encryptedData
    }

    actual inline fun <reified T : Any> T.decryptFields(vararg exclusions : String): T{
        return this
    }

    actual inline fun <reified T : Any> T.encryptFields(vararg exclusions : String): T {
        return this
    }
}