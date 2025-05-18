package org.itsivag.trackmycard.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.itsivag.trackmycard.components.CardNetwork
import org.jetbrains.compose.resources.painterResource
import trackmycard.composeapp.generated.resources.Res
import trackmycard.composeapp.generated.resources.amex
import trackmycard.composeapp.generated.resources.mastercard
import trackmycard.composeapp.generated.resources.visa

@Composable
fun CardNetworkImageMapper(network: String): Painter? =
    when (network.lowercase().trim()) {
        CardNetwork.VISA.network -> painterResource(Res.drawable.visa)
        CardNetwork.MASTERCARD.network -> painterResource(Res.drawable.mastercard)
        CardNetwork.AMEX.network -> painterResource(Res.drawable.amex)
        else -> {
            null
        }
    }