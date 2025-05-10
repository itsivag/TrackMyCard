package com.itsivag.cards.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsivag.cards.model.CardDataModel
import com.itsivag.cards.repo.CardsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardsViewModel(private val cardsRepo: CardsRepo) : ViewModel() {
    private val _card = MutableStateFlow<CardDataModel?>(null)
    val card: StateFlow<CardDataModel?> = _card.asStateFlow()

    fun getCard() {
        viewModelScope.launch(Dispatchers.IO) {
            cardsRepo.getCard("").apply {
                if (isSuccess) {
                    _card.value = getOrNull()
                } else {
                    _card.value = null
                }

            }
        }
    }
}