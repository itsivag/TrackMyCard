package com.itsivag.cards.di

import com.itsivag.cards.data.local.CardsLocalDataService
import com.itsivag.cards.data.local.CardsLocalDataServiceImpl
import com.itsivag.cards.data.remote.CardsRemoteDataService
import com.itsivag.cards.data.remote.CardsRemoteDataServiceImpl
import com.itsivag.cards.repo.CardsRepo
import com.itsivag.cards.repo.CardsRepoImpl
import com.itsivag.cards.util.httpClientWithLogger
import com.itsivag.cards.viewmodel.CardsViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val cardsPlatformModule: Module

val cardsModule = module {
    // HTTP Client
    single { httpClientWithLogger }

    // Data Services
    singleOf(::CardsRemoteDataServiceImpl).bind<CardsRemoteDataService>()
    singleOf(::CardsLocalDataServiceImpl).bind<CardsLocalDataService>()


    // Repositories
    singleOf(::CardsRepoImpl).bind<CardsRepo>()

    // ViewModels
    viewModelOf(::CardsViewModel)
} 