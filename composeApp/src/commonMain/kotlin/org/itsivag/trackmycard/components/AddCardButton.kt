package org.itsivag.trackmycard.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsivag.helper.DmSansFontFamily
import org.itsivag.trackmycard.theme.onBackgroundColor

@Composable
fun AddCardButton(modifier: Modifier, setAddCardShowBottomSheet: (Boolean) -> Unit) {
    OutlinedButton(
        modifier = modifier.fillMaxSize(),
        onClick = {
            setAddCardShowBottomSheet(true)
        },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = onBackgroundColor)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Add Card",
                modifier = Modifier.size(36.dp)
            )
            Text(
                text = "Add Card",
                fontFamily = DmSansFontFamily(),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }
    }
}