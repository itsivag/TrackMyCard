package com.itsivag.cards.data.local

import com.itsivag.models.encrypted_card.EncryptedCardDataModel
import com.itsivag.models.encrypted_card.dao.EncryptedCardsDao
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow

interface EncryptedCardLocalDataService {
    suspend fun upsertEncryptedCard(encryptedCard: EncryptedCardDataModel): Result<Boolean>
    suspend fun getAllEncryptedCards(): Result<Flow<List<EncryptedCardDataModel>>>
    suspend fun getEncryptedCardById(id: String): Result<EncryptedCardDataModel?>
    suspend fun deleteEncryptedCardById(id: String): Result<Boolean>
    suspend fun deleteAllEncryptedCards(): Result<Boolean>
}

class EncryptedCardLocalDataServiceImpl(private val encryptedCardsDao: EncryptedCardsDao) :
    EncryptedCardLocalDataService {
    override suspend fun upsertEncryptedCard(encryptedCard: EncryptedCardDataModel): Result<Boolean> {
        try {
            encryptedCardsDao.upsertEncryptedCard(encryptedCard)
            return Result.success(true)
        } catch (e: Exception) {
            Napier.e { e.stackTraceToString() }
            return Result.failure(e)
        }
    }

    override suspend fun getAllEncryptedCards(): Result<Flow<List<EncryptedCardDataModel>>> {
        try {
            val encryptedCards = encryptedCardsDao.getAllEncryptedCards()
            return Result.success(encryptedCards)
        } catch (e: Exception) {
            Napier.e { e.stackTraceToString() }
            return Result.failure(e)
        }
    }

    override suspend fun getEncryptedCardById(id: String): Result<EncryptedCardDataModel?> {
        try {
            val encryptedCard = encryptedCardsDao.getEncryptedCardById(id)
            return Result.success(encryptedCard)
        } catch (e: Exception) {
            Napier.e { e.stackTraceToString() }
            return Result.failure(e)
        }
    }

    override suspend fun deleteEncryptedCardById(id: String): Result<Boolean> {
        try {
            encryptedCardsDao.deleteEncryptedCardById(id)
            return Result.success(true)
        } catch (e: Exception) {
            Napier.e { e.stackTraceToString() }
            return Result.failure(e)
        }
    }

    override suspend fun deleteAllEncryptedCards(): Result<Boolean> {
        try {
            encryptedCardsDao.deleteAllEncryptedCards()
            return Result.success(true)
        } catch (e: Exception) {
            Napier.e { e.stackTraceToString() }
            return Result.failure(e)
        }
    }


}