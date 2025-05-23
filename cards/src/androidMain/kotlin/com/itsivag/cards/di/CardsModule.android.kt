package com.itsivag.cards.di

import com.itsivag.models.card.dao.CardsDao
import com.itsivag.models.db.TrackMyCardDatabase
import com.itsivag.models.db.getTrackMyCardDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val cardsPlatformModule = module {
    single { getTrackMyCardDatabase(androidContext()) }
    single<CardsDao> { get<TrackMyCardDatabase>().cardsDao() }
}
