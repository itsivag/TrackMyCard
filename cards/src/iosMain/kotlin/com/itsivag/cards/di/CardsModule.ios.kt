package com.itsivag.cards.di

import com.itsivag.models.card.dao.CardsDao
import com.itsivag.models.db.TrackMyCardDatabase
import com.itsivag.models.db.getTrackMyCardDatabase
import com.itsivag.models.encrypted_card.dao.EncryptedCardsDao
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val cardsPlatformModule = module {
    singleOf(::getTrackMyCardDatabase)
    single<CardsDao> { get<TrackMyCardDatabase>().cardsDao() }
    single<EncryptedCardsDao> { get<TrackMyCardDatabase>().encryptedCardsDao() }
}