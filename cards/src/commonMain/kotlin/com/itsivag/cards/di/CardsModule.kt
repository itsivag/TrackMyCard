package com.itsivag.cards.di

import com.itsivag.cards.data.CardsRemoteDataService
import com.itsivag.cards.data.CardsRemoteDataServiceImpl
import com.itsivag.cards.repo.CardsRepo
import com.itsivag.cards.repo.CardsRepoImpl
import com.itsivag.cards.util.httpClientWithLogger
import com.itsivag.cards.viewmodel.CardsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val cardsModule = module {
    // HTTP Client
    single { httpClientWithLogger }

    // Data Services
    single<CardsRemoteDataService> {
        CardsRemoteDataServiceImpl(client = get())
    }

    // Repositories
    single<CardsRepo> {
        CardsRepoImpl(cardsRemoteDataService = get())
    }

    // ViewModels
    viewModel {
        CardsViewModel(cardsRepo = get())
    }
} 