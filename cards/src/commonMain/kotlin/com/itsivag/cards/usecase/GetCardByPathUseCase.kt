package com.itsivag.cards.usecase

import com.itsivag.cards.repo.CardsRepo
import com.itsivag.models.card.CardDataModel
import io.github.aakira.napier.Napier

class GetCardByPathUseCase(private val cardsRepo: CardsRepo) {
    suspend operator fun invoke(path: String): Result<CardDataModel> {
        return try {
            if (path.isBlank()) {
                throw IllegalArgumentException("Path cannot be empty")
            }
            cardsRepo.getCardByPath(path)
        } catch (e: Exception) {
            Napier.e { e.toString() }
            Result.failure(e)
        }
    }
} 