package com.itsivag.transactions.di

import com.itsivag.transactions.data.TransactionsDao
import com.itsivag.transactions.data.TransactionsLocalDataService
import com.itsivag.transactions.data.TransactionsLocalDataServiceImpl
import com.itsivag.transactions.repo.TransactionsRepo
import com.itsivag.transactions.repo.TransactionsRepoImpl
import com.itsivag.transactions.viewmodel.TransactionsViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val transactionPlatformModule : Module

val transactionsModule = module {

    singleOf(::TransactionsLocalDataServiceImpl).bind<TransactionsLocalDataService>()
    singleOf(::TransactionsRepoImpl).bind<TransactionsRepo>()

    viewModelOf(::TransactionsViewModel)
}