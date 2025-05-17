package org.itsivag.trackmycard

import androidx.compose.ui.window.ComposeUIViewController
import org.itsivag.trackmycard.di.initKoin

fun MainViewController() = ComposeUIViewController(configure = { initKoin() }) {
//    val dao = remember { getTransactionsDatabase(UIApplication.sharedApplication).transactionsDao() }
    App()
}