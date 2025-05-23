package com.itsivag.transactions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsivag.models.transaction.TransactionDataModel
import com.itsivag.transactions.repo.TransactionsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TransactionsViewModel(private val transactionsRepo: TransactionsRepo) : ViewModel() {
    private val _transactionState = MutableStateFlow<UIState>(UIState.Loading)
    val transactionState: StateFlow<UIState> = _transactionState.asStateFlow()

    private val _transactionStateWithCardFilter = MutableStateFlow<UIState>(UIState.Loading)
    val transactionStateWithCardFilter: StateFlow<UIState> =
        _transactionStateWithCardFilter.asStateFlow()

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
            transactionsRepo.upsertTransaction(transaction)
        }
    }
}

sealed class UIState {
    data class Success(val transactionDataModel: List<TransactionDataModel>?) : UIState()
    data class Error(val message: String) : UIState()
    data object Loading : UIState()
}