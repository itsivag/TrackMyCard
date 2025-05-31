package com.itsivag.crypto

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class CryptoHelperTest {

    @Test
    fun `encryption and decryption should return original data`() {
        val cryptoHelper = CryptoHelper()
        val original = "itsivag"
        val encrypted = cryptoHelper.aesEncrypt(original.toByteArray())
        val decrypted = cryptoHelper.aesDecrypt(encrypted)

        assertEquals(expected = original, actual = String(decrypted))
    }

    @Test
    fun `encrypting the same text twice should produce different results`() {
        val cryptoHelper = CryptoHelper()
        val text = "sensitive data"

        val firstEncryption = cryptoHelper.aesEncrypt(text.toByteArray())
        val secondEncryption = cryptoHelper.aesEncrypt(text.toByteArray())

        // Due to IV usage, even with same key, encryptions should be different
        assertNotEquals(firstEncryption.toList(), secondEncryption.toList())
    }

    @Test
    fun `should handle empty strings`() {
        val cryptoHelper = CryptoHelper()
        val original = ""
        val encrypted = cryptoHelper.aesEncrypt(original.toByteArray())
        val decrypted = cryptoHelper.aesDecrypt(encrypted)

        assertEquals(expected = original, actual = String(decrypted))
    }

    @Test
    fun `should handle large text data`() {
        val cryptoHelper = CryptoHelper()
        val original = buildString {
            repeat(10000) {
                append("large text data block $it ")
            }
        }

        val encrypted = cryptoHelper.aesEncrypt(original.toByteArray())
        val decrypted = cryptoHelper.aesDecrypt(encrypted)

        assertEquals(expected = original, actual = String(decrypted))
    }

    @Test
    fun `should handle special characters`() {
        val cryptoHelper = CryptoHelper()
        val original = "Special Ch@r$%^&*()_+=-[]{}|;':,./<>?`~ characters"
        val encrypted = cryptoHelper.aesEncrypt(original.toByteArray())
        val decrypted = cryptoHelper.aesDecrypt(encrypted)

        assertEquals(expected = original, actual = String(decrypted))
    }

    @Test
    fun `should handle unicode characters`() {
        val cryptoHelper = CryptoHelper()
        val original = "Unicode: こんにちは 你好 안녕하세요 مرحبا"
        val encrypted = cryptoHelper.aesEncrypt(original.toByteArray())
        val decrypted = cryptoHelper.aesDecrypt(encrypted)

        assertEquals(expected = original, actual = String(decrypted))
    }

    @Test
    fun `encrypted data should be different from original`() {
        val cryptoHelper = CryptoHelper()
        val original = "secret message"
        val encrypted = cryptoHelper.aesEncrypt(original.toByteArray())

        // Verify encryption actually changes the data
        assertNotEquals(original.toByteArray().toList(), encrypted.toList())
    }

    @Test
    fun `encrypted data should have reasonable length`() {
        val cryptoHelper = CryptoHelper()
        val original = "This is a test message"
        val encrypted = cryptoHelper.aesEncrypt(original.toByteArray())

        // AES encrypted data should be at least as long as original
        // (due to padding and possibly stored IV)
        assertTrue(encrypted.size >= original.length)
    }
}