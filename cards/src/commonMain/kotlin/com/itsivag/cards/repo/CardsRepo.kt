package com.itsivag.cards.repo

import com.itsivag.cards.data.CardsRemoteDataService
import com.itsivag.cards.model.CardDataModel
import io.github.aakira.napier.Napier

interface CardsRepo {
    suspend fun getCard(name: String): Result<CardDataModel>
}

class CardsRepoImpl(private val cardsRemoteDataService: CardsRemoteDataService) : CardsRepo {
    override suspend fun getCard(name: String): Result<CardDataModel> {
        try {
            val card = cardsRemoteDataService.getCard(name)
            return Result.success(card)
        } catch (e: Exception) {
            Napier.v { e.toString() }
            return Result.failure(e)
        }
    }
}