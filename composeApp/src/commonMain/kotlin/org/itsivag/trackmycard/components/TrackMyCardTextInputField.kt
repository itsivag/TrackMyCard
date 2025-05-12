package org.itsivag.trackmycard.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsivag.helper.DmSansFontFamily
import org.itsivag.trackmycard.theme.backgroundColor
import org.itsivag.trackmycard.theme.focusedColor
import org.itsivag.trackmycard.theme.onBackgroundColor
import org.itsivag.trackmycard.theme.primaryColor

@Composable
internal fun TrackMyCardTextInputField(
    label: String,
    value: String,
    singleLine: Boolean = true,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        singleLine = singleLine,
        value = value,
        onValueChange = { newValue ->
            if (singleLine && newValue.length <= 20) {
                onValueChange(newValue)
            } else if (!singleLine && newValue.length <= 100) {
                onValueChange(newValue)
            }
        },
        label = { Text(label, color = Color.White, fontFamily = DmSansFontFamily()) },
        shape = RoundedCornerShape(16.dp),
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 20.sp,
            fontFamily = DmSansFontFamily(),
            fontWeight = FontWeight.Medium
        ),
        maxLines = if (singleLine) 1 else 3,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = primaryColor,
            unfocusedBorderColor = Color.Transparent,
            focusedContainerColor = focusedColor,
            unfocusedContainerColor = backgroundColor,
            cursorColor = primaryColor
        ),
        placeholder = { Text("Enter title") },
        supportingText = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                text = if (singleLine) "${value.length}/20" else "${value.length}/100",
                color = onBackgroundColor,
                fontFamily = DmSansFontFamily(),
                fontSize = 12.sp
            )
        },
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    )
}