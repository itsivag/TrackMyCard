package com.itsivag.models.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.itsivag.models.transaction.TransactionDataModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao {
    @Upsert
    suspend fun upsertTransaction(transaction: TransactionDataModel)

    @Query("SELECT * FROM transactions")
    fun getAllTransactions(): Flow<List<TransactionDataModel>>

    @Delete
    suspend fun deleteTransaction(transaction: TransactionDataModel)

    @Query("SELECT * FROM transactions WHERE cardId = :cardId")
    fun getTransactionsWithCardFilter(cardId: String): Flow<List<TransactionDataModel>>
}