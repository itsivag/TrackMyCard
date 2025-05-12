package org.itsivag.trackmycard.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsivag.helper.DmSansFontFamily

@Composable
fun TrackMyCardPrimaryButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text,
            style = TextStyle(
                fontFamily = DmSansFontFamily(),
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(8.dp)
        )
    }
}