package org.itsivag.trackmycard

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIApplication

fun MainViewController() = ComposeUIViewController {
    val dao = remember { getTransactionsDatabase(UIApplication.sharedApplication).transactionsDao() }
    App()
}