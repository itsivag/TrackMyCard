package com.itsivag.cards.repo

import com.itsivag.cards.data.local.CardsLocalDataService
import com.itsivag.cards.data.remote.CardsRemoteDataService
import com.itsivag.cards.model.CardDataModel
import com.itsivag.cards.model.CardMapperDataModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.io.files.Path

interface CardsRepo {
    suspend fun upsertCard(card: CardDataModel): Result<Boolean>
    suspend fun getAllUserCreatedCards(): Result<Flow<List<CardDataModel>>>
    suspend fun getCardMapper(): Result<CardMapperDataModel>
    suspend fun getCardByPath(path: String): Result<CardDataModel>
}

class CardsRepoImpl(
    private val cardsRemoteDataService: CardsRemoteDataService,
    private val cardsLocalDataService: CardsLocalDataService
) : CardsRepo {

    override suspend fun getCardMapper(): Result<CardMapperDataModel> {
        try {
            return Result.success(cardsRemoteDataService.getCardMapper())
        } catch (e: Exception) {
            Napier.e { e.toString() }
            return Result.failure(e)
        }
    }

    override suspend fun getCardByPath(path: String): Result<CardDataModel> {
        try {
            return Result.success(cardsRemoteDataService.getCarByPath(path))
        } catch (e: Exception) {
            Napier.e { e.toString() }
            return Result.failure(e)
        }
    }

    override suspend fun upsertCard(card: CardDataModel): Result<Boolean> {
        try {
            cardsLocalDataService.upsertCard(card)
            return Result.success(true)
        } catch (e: Exception) {
            Napier.e { e.toString() }
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