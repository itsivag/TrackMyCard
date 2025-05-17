package com.itsivag.cards.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.itsivag.cards.model.CardDataModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CardsDao {
    @Upsert
    suspend fun upsertCard(card: CardDataModel)

    @Query("SELECT * FROM cards")
    fun getAllCards(): Flow<List<CardDataModel>>
}

