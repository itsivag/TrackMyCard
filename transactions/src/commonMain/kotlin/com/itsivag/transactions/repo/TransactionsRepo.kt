package com.itsivag.transactions.repo

import com.itsivag.transactions.data.TransactionsLocalDataService
import com.itsivag.transactions.model.TransactionDataModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow

interface TransactionsRepo {
    suspend fun upsertTransaction(transaction: TransactionDataModel): Result<Boolean>
    fun getTransactions(): Result<Flow<List<TransactionDataModel>>>
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

    override fun getTransactions(): Result<Flow<List<TransactionDataModel>>> {
        try {
            val res = transactionsLocalDataService.getTransactions()
            return Result.success(res)
        } catch (e: Exception) {
            Napier.e("Error upsetting transaction", e)
            return Result.failure(e)
        }
    }

}