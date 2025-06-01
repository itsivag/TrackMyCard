package com.itsivag.cards.data.local

import androidx.compose.ui.platform.ValueElementSequence
import com.itsivag.models.encrypted_card.EncryptedCardDataModel
import com.itsivag.models.encrypted_card.EncryptedCardStorageModel
import com.itsivag.models.encrypted_card.dao.EncryptedCardsDao
import kotlinx.coroutines.flow.Flow

interface EncryptedCardLocalDataService {
    suspend fun upsertEncryptedCard(encryptedCard: EncryptedCardStorageModel)
    suspend fun getAllEncryptedCardData(): Flow<List<EncryptedCardStorageModel>>
    suspend fun getEncryptedCardById(id: String): EncryptedCardStorageModel?
    suspend fun deleteEncryptedCardById(id: String)
    suspend fun deleteAllEncryptedCards()
}

class EncryptedCardLocalDataServiceImpl(private val encryptedCardsDao: EncryptedCardsDao) :
    EncryptedCardLocalDataService {
    override suspend fun upsertEncryptedCard(encryptedCard: EncryptedCardStorageModel) =
        encryptedCardsDao.upsertEncryptedCard(encryptedCard)

    override suspend fun getAllEncryptedCardData(): Flow<List<EncryptedCardStorageModel>> =
        encryptedCardsDao.getAllEncryptedCardData()

    override suspend fun getEncryptedCardById(id: String): EncryptedCardStorageModel? =
        encryptedCardsDao.getEncryptedCardById(id)

    override suspend fun deleteEncryptedCardById(id: String) =
        encryptedCardsDao.deleteEncryptedCardById(id)

    override suspend fun deleteAllEncryptedCards() = encryptedCardsDao.deleteAllEncryptedCards()


}