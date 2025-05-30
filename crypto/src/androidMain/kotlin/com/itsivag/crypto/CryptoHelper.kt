package com.itsivag.crypto

import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

actual class CryptoHelper {
    companion object {
        private const val AES = "AES"
        private const val AES_CBC_PKCS = "AES/CBC/PKCS5Padding"
    }

    private val secretKey by lazy {
        generateAESKey()
    }

    private val iv by lazy {
        ByteArray(16).also { SecureRandom().nextBytes(it) }
    }

    private fun getCipher(): Cipher = Cipher.getInstance(AES_CBC_PKCS)

    fun generateAESKey(keySize: Int = 256): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(AES)
        keyGenerator.init(keySize)
        return keyGenerator.generateKey()
    }

    actual fun aesEncrypt(data: ByteArray): ByteArray {
        val cipher = getCipher()
        val ivParameterSpec = IvParameterSpec(iv)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
        return cipher.doFinal(data)
    }

    actual fun aesDecrypt(encryptedData: ByteArray): ByteArray {
        val cipher = getCipher()
        val ivParameterSpec =
            IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec)
        return cipher.doFinal(encryptedData)
    }
}