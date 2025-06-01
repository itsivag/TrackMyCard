package com.itsivag.crypto

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

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

        // Prepend IV to the encrypted data and encode as Base64
        return Base64.encode(iv + encrypted, Base64.NO_WRAP)
    }

    actual fun aesDecrypt(encryptedData: ByteArray): ByteArray {
        // Decode from Base64 first
        val decodedData = Base64.decode(encryptedData, Base64.NO_WRAP)
        
        // Extract IV from the beginning of the encrypted data
        val iv = decodedData.copyOfRange(0, IV_SIZE)
        val actualEncryptedData = decodedData.copyOfRange(IV_SIZE, decodedData.size)

        val cipher = getCipher()
        val ivParameterSpec = IvParameterSpec(iv)

        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec)
        return cipher.doFinal(actualEncryptedData)
    }

    actual inline fun <reified T : Any> T.encryptFields(vararg exclusions: String): T {
        val clazz = T::class
        val constructor = clazz.primaryConstructor
            ?: throw IllegalArgumentException("No primary constructor found")
        val properties = clazz.memberProperties.associate { it.name to it }

        val arguments = constructor.parameters.associate { parameter ->
            val property = properties[parameter.name] as? KProperty1<T, *>
            val value = property?.get(this)
            val encryptedValue = when {
                exclusions.contains(parameter.name) -> value
                value is String -> String(aesEncrypt(value.toByteArray()))
                value is Number -> {
                    // For numeric values, we encrypt them as strings
                    String(aesEncrypt(value.toString().toByteArray()))
                }
                else -> value
            }
            parameter to encryptedValue
        }

        return constructor.callBy(arguments)
    }

    actual inline fun <reified T : Any> T.decryptFields(vararg exclusions: String): T {
        val clazz = T::class
        val constructor = clazz.primaryConstructor
            ?: throw IllegalArgumentException("No primary constructor found")
        val properties = clazz.memberProperties.associate { it.name to it }

        val arguments = constructor.parameters.associate { parameter ->
            val property = properties[parameter.name] as? KProperty1<T, *>
            val value = property?.get(this)
            val decryptedValue = when {
                exclusions.contains(parameter.name) -> value
                value is String -> String(aesDecrypt(value.toByteArray()))
                else -> value
            }
            parameter to decryptedValue
        }

        return constructor.callBy(arguments)
    }
}