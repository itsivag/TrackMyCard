package com.itsivag.transactions.di

import com.itsivag.models.db.TrackMyCardDatabase
import com.itsivag.models.db.getTrackMyCardDatabase
import com.itsivag.models.transaction.dao.TransactionsDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val transactionPlatformModule = module {
    single { getTrackMyCardDatabase(androidContext()) }

    single<TransactionsDao> { get<TrackMyCardDatabase>().transactionsDao() }
}