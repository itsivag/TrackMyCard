package com.itsivag.cards.repo

import com.itsivag.cards.data.local.CardsLocalDataService
import com.itsivag.cards.data.remote.CardsRemoteDataService
import com.itsivag.cards.model.CardDataModel
import com.itsivag.cards.model.CardMapperDataModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow

interface CardsRepo {
    suspend fun upsertCard(card: CardDataModel): Result<Boolean>
    suspend fun getAllUserCreatedCards(): Result<Flow<List<CardDataModel>>>
    suspend fun getCardByName(name: String): Result<CardDataModel>
    suspend fun getCardMapper(): Result<CardMapperDataModel>
}

class CardsRepoImpl(
    private val cardsRemoteDataService: CardsRemoteDataService,
    private val cardsLocalDataService: CardsLocalDataService
) : CardsRepo {
    override suspend fun getCardByName(name: String): Result<CardDataModel> {
        try {
            val card = cardsRemoteDataService.getCardByName(name)
            return Result.success(card)
        } catch (e: Exception) {
            Napier.v { e.toString() }
            return Result.failure(e)
        }
    }

    override suspend fun getCardMapper(): Result<CardMapperDataModel> {
        try {
            return Result.success(cardsRemoteDataService.getCardMapper())
        } catch (e: Exception) {
            Napier.v { e.toString() }
            return Result.failure(e)
        }
    }

    override suspend fun upsertCard(card: CardDataModel): Result<Boolean> {
        try {
            cardsLocalDataService.upsertCard(card)
            return Result.success(true)
        } catch (e: Exception) {
            Napier.v { e.toString() }
            return Result.failure(e)
        }
    }

    override suspend fun getAllUserCreatedCards(): Result<Flow<List<CardDataModel>>> {
        try {
            val cards = cardsLocalDataService.getAllCards()
            return Result.success(cards)
        } catch (e: Exception) {
            Napier.e { e.toString() }
            return Result.failure(e)
        }
    }


}