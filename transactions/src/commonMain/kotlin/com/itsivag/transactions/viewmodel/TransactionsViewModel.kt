package com.itsivag.transactions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsivag.models.transaction.TransactionDataModel
import com.itsivag.transactions.error.TransactionError
import com.itsivag.transactions.repo.TransactionsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

sealed class UIState {
    data class Success(val transactionDataModel: List<TransactionDataModel>?) : UIState()
    data class Error(val message: String) : UIState()
    data object Loading : UIState()
}

sealed class UpsertTransactionUIState {
    data class Success(val transactionDataModel: TransactionDataModel) : UpsertTransactionUIState()
    data class Error(val error: TransactionError) : UpsertTransactionUIState()
    data object Loading : UpsertTransactionUIState()
    data object Idle : UpsertTransactionUIState()
}

class TransactionsViewModel(private val transactionsRepo: TransactionsRepo) : ViewModel() {
    private val _transactionState = MutableStateFlow<UIState>(UIState.Loading)
    val transactionState: StateFlow<UIState> = _transactionState.asStateFlow()

    private val _transactionStateWithCardFilter = MutableStateFlow<UIState>(UIState.Loading)
    val transactionStateWithCardFilter: StateFlow<UIState> =
        _transactionStateWithCardFilter.asStateFlow()

    private val _upsertTransactionState =
        MutableStateFlow<UpsertTransactionUIState>(UpsertTransactionUIState.Idle)
    val upsertTransactionState: StateFlow<UpsertTransactionUIState> =
        _upsertTransactionState.asStateFlow()


    init {
        getAllTransactions()
    }

    fun getAllTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = transactionsRepo.getTransactions()
            res.onSuccess {
                it.collect {
                    _transactionState.value = UIState.Success(it)
                }
            }
            res.onFailure {
                _transactionState.value = UIState.Error(it.message ?: "Error getting transactions")
            }
        }
    }

    fun getTransactionsWithCardFilter(cardId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (cardId == null || cardId.isEmpty()) {
                _transactionStateWithCardFilter.value = UIState.Error("Invalid card id")
                return@launch
            } else {
                val res = transactionsRepo.getTransactionsWithCardFilter(cardId)
                res.onSuccess {
                    it.collect {
                        _transactionStateWithCardFilter.value = UIState.Success(it)
                    }
                }

                res.onFailure {
                    _transactionStateWithCardFilter.value =
                        UIState.Error(it.message ?: "Error getting transactions")
                }
            }
        }
    }

    fun upsertTransaction(transaction: TransactionDataModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _upsertTransactionState.value = UpsertTransactionUIState.Loading
            val res = transactionsRepo.upsertTransaction(transaction)
            res.onSuccess {
                _upsertTransactionState.value = UpsertTransactionUIState.Success(transaction)
                // Reset state after a short delay to allow UI to process success
                delay(100)
                _upsertTransactionState.value = UpsertTransactionUIState.Idle
            }

            res.onFailure {
                val error = when (it.message) {
                    "Card not found" -> TransactionError.CardNotFound
                    "Title cannot be empty" -> TransactionError.TitleEmpty
                    "Title cannot be longer than 50 characters" -> TransactionError.TitleTooLong(50)
                    "Description cannot be longer than 100 characters" -> TransactionError.DescriptionTooLong(100)
                    "Amount cannot be zero" -> TransactionError.AmountZero
                    "Amount cannot be negative" -> TransactionError.AmountNegative
                    "Amount exceeds maximum limit" -> TransactionError.AmountExceedsLimit
                    "Date cannot be empty" -> TransactionError.DateEmpty
                    else -> TransactionError.Unknown(it.message ?: "Unknown error occurred")
                }
                _upsertTransactionState.value = UpsertTransactionUIState.Error(error)
            }
        }
    }

    fun clearErrorState() {
        _upsertTransactionState.value = UpsertTransactionUIState.Idle
    }
}