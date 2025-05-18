package com.itsivag.transactions.data

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

actual fun getTransactionsDatabase(context: Any): TransactionsDatabase {
    val androidContext = context as Context
    val dbFile = androidContext.getDatabasePath("transactions.db")
    return Room.databaseBuilder<TransactionsDatabase>(
        androidContext.applicationContext,
        TransactionsDatabase::class.java,
        name = dbFile.absolutePath,
    ).fallbackToDestructiveMigration(false).setDriver(BundledSQLiteDriver()).build()
}