package com.itsivag.cards.di

import com.itsivag.cards.data.local.getCardsDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val cardsPlatformModule = module {
    singleOf(::getCardsDatabase)
}