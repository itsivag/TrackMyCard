package com.itsivag.models.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.itsivag.models.card.CardDataModel
import com.itsivag.models.card.CardDataModelConverters
import com.itsivag.models.card.dao.CardsDao
import com.itsivag.models.encrypted_card.EncryptedCardDataModel
import com.itsivag.models.encrypted_card.dao.EncryptedCardsDao
import com.itsivag.models.transaction.TransactionDataModel
import com.itsivag.models.transaction.dao.TransactionsDao

@Database(
    entities = [CardDataModel::class, TransactionDataModel::class, EncryptedCardDataModel::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(CardDataModelConverters::class)
abstract class TrackMyCardDatabase : RoomDatabase() {
    abstract fun cardsDao(): CardsDao
    abstract fun transactionsDao(): TransactionsDao
    abstract fun encryptedCardsDao(): EncryptedCardsDao
}

expect fun getTrackMyCardDatabase(context: Any): TrackMyCardDatabase