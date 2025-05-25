package com.itsivag.cards.data.local

import com.itsivag.models.encrypted_card.EncryptedCardDataModel
import com.itsivag.models.encrypted_card.dao.EncryptedCardsDao
import kotlinx.coroutines.flow.Flow

interface EncryptedCardLocalDataService {
    suspend fun upsertEncryptedCard(encryptedCard: EncryptedCardDataModel)
    suspend fun getAllEncryptedCardData(): Flow<List<EncryptedCardDataModel>>
    suspend fun getEncryptedCardById(id: String): EncryptedCardDataModel?
    suspend fun deleteEncryptedCardById(id: String)
    suspend fun deleteAllEncryptedCards()
}

class EncryptedCardLocalDataServiceImpl(private val encryptedCardsDao: EncryptedCardsDao) :
    EncryptedCardLocalDataService {
    override suspend fun upsertEncryptedCard(encryptedCard: EncryptedCardDataModel) =
        encryptedCardsDao.upsertEncryptedCard(encryptedCard)

    override suspend fun getAllEncryptedCardData(): Flow<List<EncryptedCardDataModel>> =
        encryptedCardsDao.getAllEncryptedCardData()

    override suspend fun getEncryptedCardById(id: String): EncryptedCardDataModel? =
        encryptedCardsDao.getEncryptedCardById(id)

    override suspend fun deleteEncryptedCardById(id: String) =
        encryptedCardsDao.deleteEncryptedCardById(id)

    override suspend fun deleteAllEncryptedCards() = encryptedCardsDao.deleteAllEncryptedCards()


}