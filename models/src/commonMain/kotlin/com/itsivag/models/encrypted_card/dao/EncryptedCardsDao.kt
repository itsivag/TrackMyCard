package com.itsivag.models.encrypted_card.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.itsivag.models.encrypted_card.EncryptedCardDataModel
import com.itsivag.models.encrypted_card.EncryptedCardStorageModel
import kotlinx.coroutines.flow.Flow

@Dao
interface EncryptedCardsDao {
    @Upsert
    suspend fun upsertEncryptedCard(encryptedCard: EncryptedCardStorageModel)

    @Query("SELECT * FROM encrypted_cards_storage")
    fun getAllEncryptedCardData(): Flow<List<EncryptedCardStorageModel>>

    @Query("SELECT * FROM encrypted_cards_storage WHERE id = :id")
    suspend fun getEncryptedCardById(id: String): EncryptedCardStorageModel?

    @Query("DELETE FROM encrypted_cards_storage WHERE id = :id")
    suspend fun deleteEncryptedCardById(id: String)

    @Query("DELETE FROM encrypted_cards_storage")
    suspend fun deleteAllEncryptedCards()
}