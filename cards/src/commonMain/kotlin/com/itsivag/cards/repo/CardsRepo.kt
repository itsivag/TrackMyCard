package com.itsivag.cards.repo

import com.itsivag.cards.data.local.CardsLocalDataService
import com.itsivag.cards.data.local.EncryptedCardLocalDataService
import com.itsivag.cards.data.remote.CardsRemoteDataService
import com.itsivag.cards.error.CardError
import com.itsivag.models.card.CardDataModel
import com.itsivag.models.card.CardMapperDataModel
import com.itsivag.models.encrypted_card.EncryptedCardDataModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow

interface CardsRepo {
    suspend fun getAllUserCreatedCards(): Result<Flow<List<CardDataModel>>>
    suspend fun getCardMapper(): Result<CardMapperDataModel>
    suspend fun getCardByPath(path: String): Result<CardDataModel>
    suspend fun upsertCard(
        card: CardDataModel,
        encryptedCard: EncryptedCardDataModel
    ): Result<Boolean>

    suspend fun getAllEncryptedCardData(): Result<Flow<List<EncryptedCardDataModel>>>
}

class CardsRepoImpl(
    private val cardsRemoteDataService: CardsRemoteDataService,
    private val cardsLocalDataService: CardsLocalDataService,
    private val encryptedCardLocalDataService: EncryptedCardLocalDataService
) : CardsRepo {

    override suspend fun getCardMapper(): Result<CardMapperDataModel> {
        return try {
            val res = cardsRemoteDataService.getCardMapper()
            Result.success(res)
        } catch (e: Exception) {
            Napier.e("Error getting card mapper", e)
            Result.failure(e)
        }
    }

    override suspend fun getCardByPath(path: String): Result<CardDataModel> {
        return try {
            val res = cardsRemoteDataService.getCarByPath(path)
            Result.success(res)
        } catch (e: Exception) {
            Napier.e("Error getting card by path", e)
            Result.failure(e)
        }
    }

    override suspend fun upsertCard(
        card: CardDataModel,
        encryptedCard: EncryptedCardDataModel
    ): Result<Boolean> {
        return try {
            validateCard(encryptedCard)
//            encryptedCardLocalDataService.upsertEncryptedCard(encryptedCard)
            cardsLocalDataService.upsertCard(card)
            Result.success(true)
        } catch (e: CardError) {
            Napier.e("Validation error", e)
            Result.failure(e)
        } catch (e: Exception) {
            Napier.e("Error upserting card", e)
            Result.failure(CardError.Unknown(e.message ?: "Unknown error occurred"))
        }
    }

    override suspend fun getAllEncryptedCardData(): Result<Flow<List<EncryptedCardDataModel>>> {
        return try {
            val res = encryptedCardLocalDataService.getAllEncryptedCardData()
            Result.success(res)
        } catch (e: Exception) {
            Result.failure(e)
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

    private fun validateCard(encryptedCard: EncryptedCardDataModel) {
        with(encryptedCard) {
            when {
                id.isBlank() -> throw CardError.CardNotFound
                limit == 0.0 -> throw CardError.LimitZero
                limit < 0 -> throw CardError.LimitNegative
                limit > 1_000_000_000 -> throw CardError.LimitExceedsMaximum
                cycle == 0 -> throw CardError.CycleEmpty
                cycle < 1 || cycle > 31 -> throw CardError.CycleInvalid
            }
        }
    }

}