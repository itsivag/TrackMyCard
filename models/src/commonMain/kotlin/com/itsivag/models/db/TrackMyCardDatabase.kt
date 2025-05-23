package com.itsivag.models.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.itsivag.models.card.CardDataModel
import com.itsivag.models.card.CardDataModelConverters
import com.itsivag.models.card.dao.CardsDao
import com.itsivag.models.transaction.TransactionDataModel
import com.itsivag.models.transaction.dao.TransactionsDao

@Database(
    entities = [CardDataModel::class, TransactionDataModel::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(CardDataModelConverters::class)
abstract class TrackMyCardDatabase : RoomDatabase() {
    abstract fun cardsDao(): CardsDao
    abstract fun transactionsDao(): TransactionsDao
}

expect fun getTrackMyCardDatabase(context: Any): TrackMyCardDatabase