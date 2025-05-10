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
    private val _card = MutableStateFlow<UIState>(UIState.Loading)
    val card: StateFlow<UIState> = _card.asStateFlow()

    init {
        getCard()
    }

    fun getCard() {
        viewModelScope.launch(Dispatchers.IO) {
            cardsRepo.getCard("").apply {
                if (isSuccess) {
                    _card.value = UIState.Success(getOrNull())
                } else {
                    _card.value = UIState.Error(exceptionOrNull()?.message ?: "Error getting card")
                }

            }
        }
    }
}

sealed class UIState {
    data class Success(val cardDataModel: CardDataModel?) : UIState()
    data class Error(val message: String) : UIState()
    data object Loading : UIState()
}