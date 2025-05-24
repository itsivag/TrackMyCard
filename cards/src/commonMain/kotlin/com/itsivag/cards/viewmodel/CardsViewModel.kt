package com.itsivag.cards.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsivag.cards.usecase.GetAllUserCreatedCardsUseCase
import com.itsivag.cards.usecase.GetCardByPathUseCase
import com.itsivag.cards.usecase.GetCardMapperUseCase
import com.itsivag.cards.usecase.UpsertCardUseCase
import com.itsivag.models.card.CardDataModel
import com.itsivag.models.card.CardMapperDataModel
import com.itsivag.models.encrypted_card.EncryptedCardDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardsViewModel(
    private val getAllUserCreatedCardsUseCase: GetAllUserCreatedCardsUseCase,
    private val getCardMapperUseCase: GetCardMapperUseCase,
    private val getCardByPathUseCase: GetCardByPathUseCase,
    private val upsertCardUseCase: UpsertCardUseCase
) : ViewModel() {
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
            val res = getAllUserCreatedCardsUseCase()

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

    suspend fun getCardWithPath(path: String) = getCardByPathUseCase(path)

    fun upsertCard(encryptedCardDataModel: EncryptedCardDataModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val path = cardMapperState.value?.cards?.find { it.id == encryptedCardDataModel.id }?.file
            val card = path?.let { getCardWithPath(it) }
            card?.onSuccess {
                upsertCardUseCase(it)
            }
            card?.onFailure {
                // Handle error
            }
        }
    }

    fun getCardMapper() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = getCardMapperUseCase()
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
