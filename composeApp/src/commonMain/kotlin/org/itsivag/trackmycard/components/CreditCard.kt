package org.itsivag.trackmycard.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsivag.cards.model.CardDataModel
import com.itsivag.helper.OnestFontFamily
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import org.itsivag.trackmycard.theme.backgroundColor
import org.itsivag.trackmycard.theme.primaryColor


@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun CreditCard(
    card: CardDataModel,
    modifier: Modifier = Modifier,
    hazeState: HazeState
) {
    val hazeStyle = HazeMaterials.regular(containerColor = backgroundColor)
    val gradientBrush = Brush.linearGradient(
        listOf(
            Color(card.presentation.decoration.primaryColor),
            Color(card.presentation.decoration.secondaryColor),
        )
    )
    Column(
        modifier = modifier
            .border(brush = gradientBrush, width = 1.dp, shape = RoundedCornerShape(16.dp))

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
                .hazeEffect(
                    state = hazeState,
                    style = hazeStyle
                ).padding(16.dp)
        ) {
//            cardInfo.backgroundImage?.let { painter ->
//                Image(
//                    painter = painter,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxSize(),
////                        .padding((-16).dp),
//                    contentScale = ContentScale.Fit,
//                    alpha = cardInfo.backgroundImageAlpha
//                )
//            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = card.card.networkType,
                        color = Color.White,
                        fontFamily = OnestFontFamily(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = formatCardNumber(card.card.id),
                    color = Color.White,
                    fontFamily = OnestFontFamily(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 2.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "CARD HOLDER",
                            color = Color.White.copy(alpha = 0.7f),
                            fontFamily = OnestFontFamily(),
                            fontSize = 12.sp
                        )
                        Text(
                            text = "Siva G",
                            color = Color.White,
                            fontFamily = OnestFontFamily(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "EXPIRY",
                            color = Color.White.copy(alpha = 0.7f),
                            fontFamily = OnestFontFamily(),
                            fontSize = 12.sp
                        )
                        Text(
                            text = "12/34",
                            color = Color.White,
                            fontFamily = OnestFontFamily(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
        CustomLinearProgressIndicator(
            progress = 0.5f,
            modifier = Modifier.fillMaxWidth(),
            progressColor = primaryColor,
            hazeState = hazeState,
            hazeStyle = hazeStyle
        )
    }
}

private fun formatCardNumber(cardNumber: String): String {
    return cardNumber.chunked(4).joinToString(" ")
}
