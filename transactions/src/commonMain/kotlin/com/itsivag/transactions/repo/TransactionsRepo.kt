package com.itsivag.transactions.repo

import com.itsivag.models.transaction.TransactionDataModel
import com.itsivag.transactions.data.TransactionsLocalDataService
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow

interface TransactionsRepo {
    suspend fun upsertTransaction(transaction: TransactionDataModel): Result<Boolean>
    suspend fun getTransactions(): Result<Flow<List<TransactionDataModel>>>
    suspend fun getTransactionsWithCardFilter(cardId: String): Result<Flow<List<TransactionDataModel>>>
}

class TransactionsRepoImpl(private val transactionsLocalDataService: TransactionsLocalDataService) :
    TransactionsRepo {
    override suspend fun upsertTransaction(transaction: TransactionDataModel): Result<Boolean> {
        try {
            validateTransaction(transaction)
            transactionsLocalDataService.upsertTransaction(transaction)
            return Result.success(true)
        } catch (e: Exception) {
            Napier.e("Error upserting transaction", e)
            return Result.failure(e)
        }
    }

    override suspend fun getTransactions(): Result<Flow<List<TransactionDataModel>>> {
        try {
            val res = transactionsLocalDataService.getTransactions()
            return Result.success(res)
        } catch (e: Exception) {
            Napier.e("Error getting all transactions", e)
            return Result.failure(e)
        }
    }

    override suspend fun getTransactionsWithCardFilter(cardId: String): Result<Flow<List<TransactionDataModel>>> {
        try {
            if (cardId.isBlank()) {
                throw IllegalArgumentException("Card ID cannot be empty")
            }
            val res = transactionsLocalDataService.getTransactionsWithCardFilter(cardId)
            return Result.success(res)
        } catch (e: Exception) {
            Napier.e("Error getting transactions with card filter", e)
            return Result.failure(e)
        }
    }

    private fun validateTransaction(transaction: TransactionDataModel) {
        with(transaction) {
            when {
                cardId.isBlank() -> throw IllegalArgumentException("Card not found")
                title.isBlank() -> throw IllegalArgumentException("Title cannot be empty")
                title.length > 50 -> throw IllegalArgumentException("Title cannot be longer than 50 characters")
                description.length > 100 -> throw IllegalArgumentException("Description cannot be longer than 100 characters")
                amount == 0.0 -> throw IllegalArgumentException("Amount cannot be zero")
                amount < 0 -> throw IllegalArgumentException("Amount cannot be negative")
                amount > 1_000_000_000 -> throw IllegalArgumentException("Amount exceeds maximum limit")
                dateTime.isBlank() -> throw IllegalArgumentException("Date cannot be empty")
            }
        }
    }
}