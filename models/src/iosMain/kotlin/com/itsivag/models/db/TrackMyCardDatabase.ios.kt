package com.itsivag.models.db

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import platform.Foundation.NSHomeDirectory

actual fun getTrackMyCardDatabase(context: Any): TrackMyCardDatabase {
    val dbFile = NSHomeDirectory() + "/trackmycard.db"
    return Room.databaseBuilder<TrackMyCardDatabase>(
        name = dbFile,
        factory = { TrackMyCardDatabase::class.instantiateImpl() })
        .fallbackToDestructiveMigration(false)
        .setDriver(BundledSQLiteDriver())
        .build()
}