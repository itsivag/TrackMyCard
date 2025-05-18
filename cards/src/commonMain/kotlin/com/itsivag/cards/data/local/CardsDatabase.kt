package com.itsivag.cards.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.itsivag.cards.model.CardDataModel
import com.itsivag.cards.model.CardDataModelConverters

@Database(entities = [CardDataModel::class], version = 2, exportSchema = false)
@TypeConverters(CardDataModelConverters::class)
abstract class CardsDatabase : RoomDatabase() {
    abstract fun cardsDao(): CardsDao
}

expect fun getCardsDatabase(context: Any): CardsDatabase