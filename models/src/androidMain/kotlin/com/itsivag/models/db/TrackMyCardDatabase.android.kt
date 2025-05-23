package com.itsivag.models.db

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

actual fun getTrackMyCardDatabase(context: Any): TrackMyCardDatabase {
    val androidContext = context as Context
    val dbFile = androidContext.getDatabasePath("trackmycard.db")
    return Room.databaseBuilder<TrackMyCardDatabase>(
        androidContext.applicationContext,
        TrackMyCardDatabase::class.java,
        name = dbFile.absolutePath,
    ).fallbackToDestructiveMigration(false).setDriver(BundledSQLiteDriver()).build()
}