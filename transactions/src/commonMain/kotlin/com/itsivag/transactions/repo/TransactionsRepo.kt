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
            transactionsLocalDataService.upsertTransaction(transaction)
            return Result.success(true)
        } catch (e: Exception) {
            Napier.e("Error upsetting transaction", e)
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
            val res = transactionsLocalDataService.getTransactionsWithCardFilter(cardId)
            return Result.success(res)
        } catch (e: Exception) {
            Napier.e("Error getting transactions with card filter", e)
            return Result.failure(e)
        }
    }

}