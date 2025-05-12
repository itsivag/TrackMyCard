package com.itsivag.transactions.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.itsivag.transactions.model.TransactionDataModel

@Database(entities = [TransactionDataModel::class], version = 1, exportSchema = false)
abstract class TransactionsDatabase : RoomDatabase() {
    abstract fun transactionsDao(): TransactionsDao
}

expect fun getTransactionsDatabase(context: Any): TransactionsDatabase

