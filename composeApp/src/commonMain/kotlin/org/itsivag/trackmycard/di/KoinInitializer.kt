package org.itsivag.trackmycard.di

import com.itsivag.cards.di.cardsModule
import com.itsivag.cards.di.cardsPlatformModule
import com.itsivag.transactions.di.transactionPlatformModule
import com.itsivag.transactions.di.transactionsModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(cardsModule, cardsPlatformModule, transactionsModule, transactionPlatformModule)
    }
}
