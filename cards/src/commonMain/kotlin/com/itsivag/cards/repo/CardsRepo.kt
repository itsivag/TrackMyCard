package com.itsivag.cards.repo

import com.itsivag.cards.data.local.CardsLocalDataService
import com.itsivag.cards.data.local.EncryptedCardLocalDataService
import com.itsivag.cards.data.remote.CardsRemoteDataService
import com.itsivag.cards.error.CardError
import com.itsivag.crypto.CryptoHelper
import com.itsivag.models.card.CardDataModel
import com.itsivag.models.card.CardMapperDataModel
import com.itsivag.models.encrypted_card.EncryptedCardDataModel
import com.itsivag.models.encrypted_card.EncryptedCardStorageModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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
    private val encryptedCardLocalDataService: EncryptedCardLocalDataService,
    private val cryptoHelper: CryptoHelper
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
        try {
            validateCard(encryptedCard)
            cardsLocalDataService.upsertCard(card)
            with(cryptoHelper) {
                val storageModel = EncryptedCardStorageModel(
                    id = encryptedCard.id,
                    limit = encryptedCard.limit.toString(),
                    cycle = encryptedCard.cycle.toString(),
                    cardHolderName = encryptedCard.cardHolderName
                ).encryptFields(exclusions = arrayOf("id"))
                
                encryptedCardLocalDataService.upsertEncryptedCard(storageModel)
            }
            return Result.success(true)
        } catch (e: CardError) {
            Napier.e("Validation error", e)
            return Result.failure(e)
        } catch (e: Exception) {
            Napier.e("Error upserting card", e)
            return Result.failure(CardError.Unknown(e.message ?: "Unknown error occurred"))
        }
    }

    override suspend fun getAllEncryptedCardData(): Result<Flow<List<EncryptedCardDataModel>>> {
        return try {
            val res = encryptedCardLocalDataService.getAllEncryptedCardData()
            val convertedFlow = res.map { list ->
                list.map { storageModel ->
                    try {
                        with(cryptoHelper) {
                            val decryptedModel = storageModel.decryptFields(exclusions = arrayOf("id"))
                            EncryptedCardDataModel(
                                id = decryptedModel.id,
                                limit = decryptedModel.limit.toDouble(),
                                cycle = decryptedModel.cycle.toInt(),
                                cardHolderName = decryptedModel.cardHolderName
                            )
                        }
                    } catch (e: Exception) {
                        Napier.e("Error decrypting card data", e)
                        EncryptedCardDataModel(
                            id = storageModel.id,
                            limit = 0.0,
                            cycle = 1,
                            cardHolderName = "Error decrypting card"
                        )
                    }
                }
            }
            Result.success(convertedFlow)
        } catch (e: Exception) {
            Napier.e("Error getting encrypted card data", e)
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
                cardHolderName.isBlank() -> throw CardError.CardHolderNameEmpty
            }
        }
    }

}