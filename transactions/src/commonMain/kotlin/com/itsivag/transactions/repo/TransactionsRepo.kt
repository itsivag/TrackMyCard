package com.itsivag.transactions.repo

import com.itsivag.models.transaction.TransactionDataModel
import com.itsivag.transactions.data.TransactionsLocalDataService
import com.itsivag.transactions.error.TransactionError
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow

interface TransactionsRepo {
    suspend fun upsertTransaction(transaction: TransactionDataModel): Result<Boolean>
    suspend fun getTransactions(): Result<Flow<List<TransactionDataModel>>>
    suspend fun getTransactionsWithCardFilter(cardId: String): Result<Flow<List<TransactionDataModel>>>
    suspend fun getUtilisedLimit(cardId: String): Result<Flow<Double>>
}

class TransactionsRepoImpl(private val transactionsLocalDataService: TransactionsLocalDataService) :
    TransactionsRepo {

    override suspend fun upsertTransaction(transaction: TransactionDataModel): Result<Boolean> {
        return try {
            validateTransaction(transaction)
            transactionsLocalDataService.upsertTransaction(transaction)
            Result.success(true)
        } catch (e: TransactionError) {
            Napier.e("Validation error", e)
            Result.failure(e)
        } catch (e: Exception) {
            Napier.e("Error upserting transaction", e)
            Result.failure(TransactionError.Unknown(e.message ?: "Unknown error occurred"))
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
                throw TransactionError.CardNotFound
            }
            val res = transactionsLocalDataService.getTransactionsWithCardFilter(cardId)

            return Result.success(res)
        } catch (e: Exception) {
            Napier.e("Error getting transactions with card filter", e)
            return Result.failure(e)
        }
    }

    override suspend fun getUtilisedLimit(cardId: String): Result<Flow<Double>> {
        return try {
            val res = transactionsLocalDataService.getUtilisedAmountForCard(cardId)
            Result.success(res)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun validateTransaction(transaction: TransactionDataModel) {
        with(transaction) {
            when {
                cardId.isBlank() -> throw TransactionError.CardNotFound
                title.isBlank() -> throw TransactionError.TitleEmpty
                title.length > 50 -> throw TransactionError.TitleTooLong(50)
                description.length > 100 -> throw TransactionError.DescriptionTooLong(100)
                amount == 0.0 -> throw TransactionError.AmountZero
                amount < 0 -> throw TransactionError.AmountNegative
                amount > 1_000_000_000 -> throw TransactionError.AmountExceedsLimit
                dateTime.isBlank() -> throw TransactionError.DateEmpty
            }
        }
    }
}