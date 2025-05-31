package com.itsivag.crypto

import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

actual class CryptoHelper {
    companion object {
        private const val AES = "AES"
        private const val AES_CBC_PKCS = "AES/CBC/PKCS5Padding"
        private const val IV_SIZE = 16 // AES block size
    }

    private val secretKey by lazy {
        generateAESKey()
    }

    private fun getCipher(): Cipher = Cipher.getInstance(AES_CBC_PKCS)

    fun generateAESKey(keySize: Int = 256): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(AES)
        keyGenerator.init(keySize)
        return keyGenerator.generateKey()
    }

    actual fun aesEncrypt(data: ByteArray): ByteArray {
        // Generate a new random IV for each encryption
        val iv = ByteArray(IV_SIZE).also { SecureRandom().nextBytes(it) }
        val cipher = getCipher()
        val ivParameterSpec = IvParameterSpec(iv)

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
        val encrypted = cipher.doFinal(data)

        // Prepend IV to the encrypted data so we can use it for decryption
        return iv + encrypted
    }

    actual fun aesDecrypt(encryptedData: ByteArray): ByteArray {
        // Extract IV from the beginning of the encrypted data
        val iv = encryptedData.copyOfRange(0, IV_SIZE)
        val actualEncryptedData = encryptedData.copyOfRange(IV_SIZE, encryptedData.size)

        val cipher = getCipher()
        val ivParameterSpec = IvParameterSpec(iv)

        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec)
        return cipher.doFinal(actualEncryptedData)
    }

    actual inline fun <reified T : Any> T.encryptFields(): T {
        val clazz = T::class.java
        val constructor = clazz.declaredConstructors.first()
        constructor.isAccessible = true
        val properties = clazz.kotlin.memberProperties
        val encryptedValues = properties.map { property ->
            val value = (property as KProperty1<T, *>).get(this)
            if (value is String) {
                aesEncrypt(value.toByteArray()).toString()
            } else {
                value
            }
        }.toTypedArray()
        @Suppress("UNCHECKED_CAST")
        return constructor.newInstance(*encryptedValues) as T
    }

    actual inline fun <reified T : Any> T.decryptFields(cryptoHelper: CryptoHelper): T {
        val clazz = T::class.java
        val constructor = clazz.declaredConstructors.first()
        constructor.isAccessible = true
        val properties = clazz.kotlin.memberProperties
        val decryptedValues = properties.map { property ->
            val value = (property as KProperty1<T, *>).get(this)
            if (value is String) {
                String(cryptoHelper.aesDecrypt(value.toByteArray()))
            } else {
                value
            }
        }.toTypedArray()
        @Suppress("UNCHECKED_CAST")
        return constructor.newInstance(*decryptedValues) as T
    }

}