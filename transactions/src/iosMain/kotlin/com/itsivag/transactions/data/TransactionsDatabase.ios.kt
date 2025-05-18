package com.itsivag.transactions.data

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import platform.Foundation.NSHomeDirectory

actual fun getTransactionsDatabase(context: Any): TransactionsDatabase {
    val dbFile = NSHomeDirectory() + "/transactions.db"
    return Room.databaseBuilder<TransactionsDatabase>(
        name = dbFile,
        factory = { TransactionsDatabase::class.instantiateImpl() })
        .fallbackToDestructiveMigration(false)
        .setDriver(BundledSQLiteDriver())
        .build()
}