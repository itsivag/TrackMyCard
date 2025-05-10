package org.itsivag.trackmycard

import platform.UIKit.UITraitCollection
import platform.UIKit.UITraitCollection.currentTraitCollection

actual fun isSystemInDarkTheme(): Boolean {
    return currentTraitCollection.userInterfaceStyle == UITraitCollection.UserInterfaceStyle.UIUserInterfaceStyleDark
} 