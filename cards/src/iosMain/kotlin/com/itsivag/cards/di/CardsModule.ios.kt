package com.itsivag.cards.di

import com.itsivag.models.db.getTrackMyCardDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val cardsPlatformModule = module {
    singleOf(::getTrackMyCardDatabase)
}