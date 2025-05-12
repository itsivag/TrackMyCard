package com.itsivag.transactions.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.itsivag.transactions.model.TransactionDataModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao {
    @Upsert
    suspend fun upsertTransaction(transaction: TransactionDataModel)

    @Query("SELECT * FROM transactions")
    fun getAllTransactions(): Flow<List<TransactionDataModel>>

    @Delete
    suspend fun deleteTransaction(transaction: TransactionDataModel)
}