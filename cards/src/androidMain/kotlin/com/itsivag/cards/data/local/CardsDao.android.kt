package com.itsivag.cards.data.local

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.itsivag.models.db.TrackMyCardDatabase

//actual fun getCardsDatabase(context: Any): TrackMyCardDatabase {
//    val androidContext = context as Context
//    val dbFile = androidContext.getDatabasePath("cards.db")
//    return Room.databaseBuilder<TrackMyCardDatabase>(
//        androidContext.applicationContext,
//        TrackMyCardDatabase::class.java,
//        name = dbFile.absolutePath,
//    ).fallbackToDestructiveMigration(false)
//        .setDriver(BundledSQLiteDriver()).build()
//}