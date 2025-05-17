package com.itsivag.cards.data.local

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import platform.Foundation.NSHomeDirectory

actual fun getCardsDatabase(context: Any): CardsDatabase {
    val dbFile = NSHomeDirectory() + "/cards.db"
    return Room.databaseBuilder<CardsDatabase>(
        name = dbFile,
        factory = { CardsDatabase::class.instantiateImpl() })
        .setDriver(BundledSQLiteDriver())
        .build()
}