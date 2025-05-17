package com.itsivag.cards.data.local

import com.itsivag.cards.model.CardDataModel
import kotlinx.coroutines.flow.Flow

interface CardsLocalDataService {
    suspend fun upsertCard(card: CardDataModel)
    fun getAllCards(): Flow<List<CardDataModel>>

}

class CardsLocalDataServiceImpl(private val cardsDao: CardsDao) : CardsLocalDataService {
    override suspend fun upsertCard(card: CardDataModel) {
        cardsDao.upsertCard(card)
    }

    override fun getAllCards(): Flow<List<CardDataModel>> = cardsDao.getAllCards()

}