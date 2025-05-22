package org.itsivag.trackmycard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.itsivag.cards.model.CardDataModel
import com.itsivag.helper.DmSansFontFamily
import org.itsivag.trackmycard.utils.CardNetworkImageMapper

@Composable
fun MiniCreditCard(card: CardDataModel?) {
    Box(
        modifier = Modifier.background(
            color = Color(card?.presentation?.decoration?.primaryColor ?: 255255255),
            shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                card?.card?.cardName ?: "",
                fontFamily = DmSansFontFamily(),
                fontWeight = FontWeight.Medium
            )
            CardNetworkImageMapper(card?.card?.networkType ?: "")?.let {
                Image(
                    modifier = Modifier.height(16.dp),
                    painter = it,
                    contentDescription = it.toString()
                )
            }
        }
    }
}