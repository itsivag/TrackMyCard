package com.itsivag.cards.data.local

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

actual fun getCardsDatabase(context: Any): CardsDatabase {
    val androidContext = context as Context
    val dbFile = androidContext.getDatabasePath("cards.db")
    return Room.databaseBuilder<CardsDatabase>(
        androidContext.applicationContext,
        CardsDatabase::class.java,
        name = dbFile.absolutePath,
    ).fallbackToDestructiveMigration(false)
        .setDriver(BundledSQLiteDriver()).build()
}