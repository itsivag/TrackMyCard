package com.itsivag.cards.usecase

import com.itsivag.cards.repo.CardsRepo
import com.itsivag.models.card.CardMapperDataModel
import io.github.aakira.napier.Napier

class GetCardMapperUseCase(private val cardsRepo: CardsRepo) {
    suspend operator fun invoke(): Result<CardMapperDataModel> {
        return try {
            cardsRepo.getCardMapper()
        } catch (e: Exception) {
            Napier.e { e.toString() }
            Result.failure(e)
        }
    }
} 