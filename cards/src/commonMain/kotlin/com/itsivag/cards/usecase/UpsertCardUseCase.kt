package com.itsivag.cards.usecase

import com.itsivag.cards.repo.CardsRepo
import com.itsivag.models.card.CardDataModel
import io.github.aakira.napier.Napier

class UpsertCardUseCase(private val cardsRepo: CardsRepo) {
    suspend operator fun invoke(card: CardDataModel): Result<Boolean> {
        return try {
            validateCard(card)
            cardsRepo.upsertCard(card)
        } catch (e: Exception) {
            Napier.e { e.toString() }
            Result.failure(e)
        }
    }

    private fun validateCard(card: CardDataModel) {
        when {
            card.card.cardName.isBlank() -> throw IllegalArgumentException("Card name cannot be empty")
            card.card.cardName.length > 50 -> throw IllegalArgumentException("Card name cannot be longer than 50 characters")
            card.presentation.description.length > 100 -> throw IllegalArgumentException("Description cannot be longer than 100 characters")
        }
    }
} 