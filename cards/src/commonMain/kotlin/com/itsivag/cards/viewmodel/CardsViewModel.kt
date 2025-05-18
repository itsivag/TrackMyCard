package com.itsivag.cards.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsivag.cards.model.CardDataModel
import com.itsivag.cards.repo.CardsRepo
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardsViewModel(private val cardsRepo: CardsRepo) : ViewModel() {
    private val _cardState = MutableStateFlow<UIState>(UIState.Loading)
    val cardState: StateFlow<UIState> = _cardState.asStateFlow()

    init {
        getAllCards()
    }

    fun getAllCards() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = cardsRepo.getAllCards()

            res.onSuccess {
                it.collect {
                    _cardState.value = UIState.Success(it)
                }
            }

            res.onFailure {
                _cardState.value = UIState.Error(it.message ?: "Error getting card")

            }
        }
    }

    suspend fun getCardByName(name: String) = cardsRepo.getCardByName(name)

    fun upsertCard(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val card = getCardByName(name)
            card.onSuccess {
                cardsRepo.upsertCard(it)
            }
        }
    }
}

sealed class UIState {
    data class Success(val cardDataModel: List<CardDataModel>?) : UIState()
    data class Error(val message: String) : UIState()
    data object Loading : UIState()
}