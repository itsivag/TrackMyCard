package com.itsivag.cards.usecase

import com.itsivag.cards.repo.CardsRepo
import com.itsivag.models.card.CardDataModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow

class GetAllUserCreatedCardsUseCase(private val cardsRepo: CardsRepo) {
    suspend operator fun invoke(): Result<Flow<List<CardDataModel>>> {
        return try {
            cardsRepo.getAllUserCreatedCards()
        } catch (e: Exception) {
            Napier.e { e.toString() }
            Result.failure(e)
        }
    }
} 