package org.itsivag.trackmycard.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.itsivag.helper.DmSansFontFamily
import com.itsivag.helper.OnestFontFamily
import org.itsivag.trackmycard.components.CardPager
import org.itsivag.trackmycard.components.CreditCardInfo
import org.itsivag.trackmycard.components.TransactionListItem
import org.itsivag.trackmycard.theme.backgroundColor
import org.itsivag.trackmycard.theme.onBackgroundColor
import org.itsivag.trackmycard.theme.primaryColor

@Composable
internal fun HomeScreen(
    paddingValues: PaddingValues,
    navigateToTransactionsScreen: () -> Unit,
) {

    val sampleCards = remember {
        listOf(
            CreditCardInfo(
                cardNumber = "4532 1234 5678 9012",
                cardHolderName = "JOHN DOE",
                expiryDate = "12/25",
                cardType = "VISA",
                gradientColors = listOf(Color(0xFF1A237E), Color(0xFF3949AB))
            ),
            CreditCardInfo(
                cardNumber = "5123 4567 8901 2345",
                cardHolderName = "JANE SMITH",
                expiryDate = "09/24",
                cardType = "MASTERCARD",
                gradientColors = listOf(Color(0xFFB71C1C), Color(0xFFD32F2F))
            ),
            CreditCardInfo(
                cardNumber = "3782 8224 6310 0055",
                cardHolderName = "MIKE JOHNSON",
                expiryDate = "03/26",
                cardType = "AMEX",
                gradientColors = listOf(Color(0xFF1B5E20), Color(0xFF2E7D32))
            )
        )
    }
    val config = LocalWindowInfo.current
    val height = rememberSaveable { config.containerSize.height }

    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        item {
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data("https://raw.githubusercontent.com/itsivag/TrackMyCardPublicData/main/sample.webp")
                        .crossfade(true)
                        .build(),
                    contentDescription = "WebP Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height((height * 0.12).dp)
                )
                CardPager(
                    cards = sampleCards,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
        item {
            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                OutlinedButton(
                    onClick = {},
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = onBackgroundColor)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add Transaction",
                            modifier = Modifier.size(36.dp)
                        )
                        Text(
                            text = "Add Transaction",
                            fontFamily = DmSansFontFamily(),
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
        item {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Transactions",
                    fontFamily = OnestFontFamily(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                )
                Spacer(Modifier.weight(1f))
                TextButton(onClick = navigateToTransactionsScreen) {
                    Text(
                        text = "See All",
                        fontFamily = OnestFontFamily(),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = primaryColor
                    )
                }
            }
        }
        items(10) {
            TransactionListItem()
        }
    }
}


