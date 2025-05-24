package com.itsivag.models.encrypted_card.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.itsivag.models.encrypted_card.EncryptedCardDataModel
import kotlinx.coroutines.flow.Flow

@Dao
interface EncryptedCardsDao {
    @Upsert
    suspend fun upsertEncryptedCard(encryptedCard: EncryptedCardDataModel)

    @Query("SELECT * FROM encrypted_cards")
    fun getAllEncryptedCards(): Flow<List<EncryptedCardDataModel>>

    @Query("SELECT * FROM encrypted_cards WHERE id = :id")
    suspend fun getEncryptedCardById(id: String): EncryptedCardDataModel?

    @Query("DELETE FROM encrypted_cards WHERE id = :id")
    suspend fun deleteEncryptedCardById(id: String)

    @Query("DELETE FROM encrypted_cards")
    suspend fun deleteAllEncryptedCards()
}