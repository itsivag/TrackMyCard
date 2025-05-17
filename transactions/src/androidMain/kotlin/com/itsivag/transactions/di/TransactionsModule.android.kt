package com.itsivag.transactions.di

import com.itsivag.transactions.data.TransactionsDao
import com.itsivag.transactions.data.getTransactionsDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val transactionPlatformModule = module {
    single { getTransactionsDatabase(androidContext()) }

    single<TransactionsDao> { get<com.itsivag.transactions.data.TransactionsDatabase>().transactionsDao() }
}