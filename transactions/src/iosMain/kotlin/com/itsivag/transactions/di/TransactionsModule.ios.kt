package com.itsivag.transactions.di

import com.itsivag.transactions.data.getTransactionsDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val transactionPlatformModule = module {
    singleOf(::getTransactionsDatabase)
}