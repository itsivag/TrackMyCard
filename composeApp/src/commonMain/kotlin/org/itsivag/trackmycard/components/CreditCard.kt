package org.itsivag.trackmycard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsivag.helper.OnestFontFamily

data class CreditCardInfo(
    val cardNumber: String,
    val cardHolderName: String,
    val expiryDate: String,
    val cardType: String = "VISA", // Default to VISA, can be changed
    val gradientColors: List<Color>,
    val backgroundImage: Painter? = null,
    val backgroundImageAlpha: Float = 0.2f
)

@Composable
fun defaultGradientColors(): List<Color> {
    return listOf(
        MaterialTheme.colors.primary,
        MaterialTheme.colors.primaryVariant
    )
}

@Composable
fun CreditCard(
    cardInfo: CreditCardInfo,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = cardInfo.gradientColors
                )
            )
            .padding(16.dp)
    ) {
        // Background Image
        cardInfo.backgroundImage?.let { painter ->
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(-16.dp),
                contentScale = ContentScale.Crop,
                alpha = cardInfo.backgroundImageAlpha
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Card Type and Logo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = cardInfo.cardType,
                    color = MaterialTheme.colors.onPrimary,
                    fontFamily = OnestFontFamily(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                // You can add card provider logo here
            }

            // Card Number
            Text(
                text = formatCardNumber(cardInfo.cardNumber),
                color = MaterialTheme.colors.onPrimary,
                fontFamily = OnestFontFamily(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 2.sp
            )

            // Card Holder Name and Expiry Date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "CARD HOLDER",
                        color = MaterialTheme.colors.onPrimary.copy(alpha = 0.7f),
                        fontFamily = OnestFontFamily(),
                        fontSize = 12.sp
                    )
                    Text(
                        text = cardInfo.cardHolderName,
                        color = MaterialTheme.colors.onPrimary,
                        fontFamily = OnestFontFamily(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "EXPIRY",
                        color = MaterialTheme.colors.onPrimary.copy(alpha = 0.7f),
                        fontFamily = OnestFontFamily(),
                        fontSize = 12.sp
                    )
                    Text(
                        text = cardInfo.expiryDate,
                        color = MaterialTheme.colors.onPrimary,
                        fontFamily = OnestFontFamily(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

private fun formatCardNumber(cardNumber: String): String {
    return cardNumber.chunked(4).joinToString(" ")
} 
