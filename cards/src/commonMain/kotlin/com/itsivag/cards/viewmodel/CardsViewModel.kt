package com.itsivag.cards.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsivag.cards.error.CardError
import com.itsivag.cards.repo.CardsRepo
import com.itsivag.models.card.CardDataModel
import com.itsivag.models.card.CardMapperDataModel
import com.itsivag.models.encrypted_card.EncryptedCardDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardsViewModel(private val cardsRepo: CardsRepo) : ViewModel() {
    private val _cardState =
        MutableStateFlow<UserCreatedCardUIState>(UserCreatedCardUIState.Idle)
    val cardState: StateFlow<UserCreatedCardUIState> = _cardState.asStateFlow()

    private val _cardMapperState =
        MutableStateFlow<CardMapperDataModel?>(null)
    val cardMapperState: StateFlow<CardMapperDataModel?> = _cardMapperState.asStateFlow()

    private val _upsertCardState = MutableStateFlow<UpsertCardUIState>(UpsertCardUIState.Idle)
    val upsertCardState: StateFlow<UpsertCardUIState> = _upsertCardState.asStateFlow()

    private val _encryptedCardState =
        MutableStateFlow<EncryptedCardUIState>(EncryptedCardUIState.Idle)
    val encryptedCardState: StateFlow<EncryptedCardUIState> = _encryptedCardState.asStateFlow()

    init {
        getUserCreatedCards()
        getCardMapper()
        getAllEncryptedCardData()
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

    suspend fun getCardWithPath(path: String) = cardsRepo.getCardByPath(path)

    fun upsertCard(encryptedCardDataModel: EncryptedCardDataModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _upsertCardState.value = UpsertCardUIState.Loading
            val path =
                cardMapperState.value?.cards?.find { it.id == encryptedCardDataModel.id }?.file
            if (path == null) {
                _upsertCardState.value = UpsertCardUIState.Error(CardError.CardNotFound)
                return@launch
            }

            val card = getCardWithPath(path)
            card.onSuccess {
                val res = cardsRepo.upsertCard(card = it, encryptedCard = encryptedCardDataModel)
                res.onSuccess {
                    _upsertCardState.value = UpsertCardUIState.Success(encryptedCardDataModel)
                }

                res.onFailure {
                    val error = when (it.message) {
                        "Card not found" -> CardError.CardNotFound
                        "Limit cannot be zero" -> CardError.LimitZero
                        "Limit cannot be negative" -> CardError.LimitNegative
                        "Limit exceeds maximum limit" -> CardError.LimitExceedsMaximum
                        else -> CardError.Unknown(it.message ?: "Unknown error occurred")

                    }
                    _upsertCardState.value = UpsertCardUIState.Error(error)
                }
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

    fun getAllEncryptedCardData() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = cardsRepo.getAllEncryptedCardData()
            res.onSuccess {
                it.collect {
                    _encryptedCardState.value = EncryptedCardUIState.Success(it)
                }
            }

            res.onFailure {
                _encryptedCardState.value =
                    EncryptedCardUIState.Error(CardError.Unknown(it.message ?: "Error adding card"))
            }
        }
    }

    fun clearErrorState() {
        _upsertCardState.value = UpsertCardUIState.Idle
    }
}

sealed class UserCreatedCardUIState {
    data class Success(val cardDataModel: List<CardDataModel>?) : UserCreatedCardUIState()
    data class Error(val message: String) : UserCreatedCardUIState()
    data object Loading : UserCreatedCardUIState()
    data object Idle : UserCreatedCardUIState()
}

sealed class UpsertCardUIState {
    data class Success(val encryptedCardDataModel: EncryptedCardDataModel) : UpsertCardUIState()
    data class Error(val error: CardError) : UpsertCardUIState()
    data object Loading : UpsertCardUIState()
    data object Idle : UpsertCardUIState()
}

sealed class EncryptedCardUIState {
    data class Success(val encryptedCardDataModelList: List<EncryptedCardDataModel>) :
        EncryptedCardUIState()

    data class Error(val error: CardError) : EncryptedCardUIState()
    data object Loading : EncryptedCardUIState()
    data object Idle : EncryptedCardUIState()
}

