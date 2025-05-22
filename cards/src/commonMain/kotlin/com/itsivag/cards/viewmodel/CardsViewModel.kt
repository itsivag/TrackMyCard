package com.itsivag.cards.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsivag.cards.model.CardDataModel
import com.itsivag.cards.model.CardMapperDataModel
import com.itsivag.cards.repo.CardsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardsViewModel(private val cardsRepo: CardsRepo) : ViewModel() {
    private val _cardState =
        MutableStateFlow<UserCreatedCardUIState>(UserCreatedCardUIState.Loading)
    val cardState: StateFlow<UserCreatedCardUIState> = _cardState.asStateFlow()

    private val _cardMapperState =
        MutableStateFlow<CardMapperDataModel?>(null)
    val cardMapperState: StateFlow<CardMapperDataModel?> = _cardMapperState.asStateFlow()

    init {
        getUserCreatedCards()
        getCardMapper()
    }

    fun getUserCreatedCards() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = cardsRepo.getAllUserCreatedCards()

            res.onSuccess {
                it.collect {
                    _cardState.value = UserCreatedCardUIState.Success(it)
                }
            }

            res.onFailure {
                _cardState.value = UserCreatedCardUIState.Error(it.message ?: "Error getting card")

            }
        }
    }

    suspend fun getCardWithPath(path : String) = cardsRepo.getCardByPath(path)

    fun upsertCard(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val path = cardMapperState.value?.cards?.find { it.name == name }?.file
            val card = path?.let { getCardWithPath(it) }
            card?.onSuccess {
                cardsRepo.upsertCard(it)
            }
            card?.onFailure {

            }
        }
    }

    fun getCardMapper() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = cardsRepo.getCardMapper()
            res.onSuccess {
                _cardMapperState.value = it
            }
        }
    }
}

sealed class UserCreatedCardUIState {
    data class Success(val cardDataModel: List<CardDataModel>?) : UserCreatedCardUIState()
    data class Error(val message: String) : UserCreatedCardUIState()
    data object Loading : UserCreatedCardUIState()
}
