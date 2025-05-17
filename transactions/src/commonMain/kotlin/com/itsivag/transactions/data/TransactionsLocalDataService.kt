package com.itsivag.transactions.data

import com.itsivag.transactions.model.TransactionDataModel
import kotlinx.coroutines.flow.Flow

interface TransactionsLocalDataService {
    suspend fun upsertTransaction(transaction: TransactionDataModel)
    fun getTransactions(): Flow<List<TransactionDataModel>>
}

class TransactionsLocalDataServiceImpl(private val transactionsDao: TransactionsDao) :
    TransactionsLocalDataService {
    override suspend fun upsertTransaction(transaction: TransactionDataModel) =
        transactionsDao.upsertTransaction(transaction)

    override fun getTransactions(): Flow<List<TransactionDataModel>> = transactionsDao.getAllTransactions()

}
