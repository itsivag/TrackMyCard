package com.itsivag.cards.data.local

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.itsivag.models.db.TrackMyCardDatabase
import platform.Foundation.NSHomeDirectory

actual fun getCardsDatabase(context: Any): TrackMyCardDatabase {
    val dbFile = NSHomeDirectory() + "/cards.db"
    return Room.databaseBuilder<TrackMyCardDatabase>(
        name = dbFile,
        factory = { TrackMyCardDatabase::class.instantiateImpl() })
        .fallbackToDestructiveMigration(false)
        .setDriver(BundledSQLiteDriver())
        .build()
}