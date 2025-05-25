package com.itsivag.transactions.data

import com.itsivag.models.transaction.TransactionDataModel
import com.itsivag.models.transaction.dao.TransactionsDao
import kotlinx.coroutines.flow.Flow

interface TransactionsLocalDataService {
    suspend fun upsertTransaction(transaction: TransactionDataModel)
    suspend fun getTransactions(): Flow<List<TransactionDataModel>>
    suspend fun getTransactionsWithCardFilter(cardId: String): Flow<List<TransactionDataModel>>
    suspend fun getUtilisedAmountForCard(cardId: String): Flow<Double>
}

class TransactionsLocalDataServiceImpl(private val transactionsDao: TransactionsDao) :
    TransactionsLocalDataService {
    override suspend fun upsertTransaction(transaction: TransactionDataModel) =
        transactionsDao.upsertTransaction(transaction)

    override suspend fun getTransactions(): Flow<List<TransactionDataModel>> =
        transactionsDao.getAllTransactions()

    override suspend fun getTransactionsWithCardFilter(cardId: String) =
        transactionsDao.getTransactionsWithCardFilter(cardId)

    override suspend fun getUtilisedAmountForCard(cardId: String): Flow<Double> =
        transactionsDao.getUtilisedAmountForCard(cardId)

}
