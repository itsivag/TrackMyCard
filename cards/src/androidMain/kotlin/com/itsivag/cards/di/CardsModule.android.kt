package com.itsivag.cards.di

import com.itsivag.cards.data.local.CardsDao
import com.itsivag.cards.data.local.getCardsDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val cardsPlatformModule = module {
    single { getCardsDatabase(androidContext()) }
    single<CardsDao> { get<com.itsivag.cards.data.local.CardsDatabase>().cardsDao() }
}
