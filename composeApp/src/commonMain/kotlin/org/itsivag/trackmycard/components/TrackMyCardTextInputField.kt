package org.itsivag.trackmycard.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import org.itsivag.trackmycard.theme.primaryColor

@Composable
internal fun TrackMyCardTextInputField(
    label: String,
    value: String,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    showCharacterCount: Boolean = false,
    error: String? = null,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        value = value,
        onValueChange = { newValue ->
            if (singleLine && newValue.length <= 50) {
                onValueChange(newValue)
            } else if (!singleLine) {
                onValueChange(newValue)
            }
        },
        readOnly = readOnly,
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
            focusedBorderColor = if (error != null) Color.Red else primaryColor,
            unfocusedBorderColor = if (error != null) Color.Red else Color.Transparent,
            focusedContainerColor = focusedColor,
            unfocusedContainerColor = backgroundColor,
            cursorColor = primaryColor,
            errorBorderColor = Color.Red,
            errorLabelColor = Color.Red,
            errorSupportingTextColor = Color.Red,
            errorCursorColor = Color.Red
        ),
        placeholder = { Text("Enter $label") },
        supportingText = {
            if (error != null) {
                Text(
                    text = error,
                    color = Color.Red,
                    fontFamily = DmSansFontFamily(),
                    fontSize = 12.sp
                )
            } else if (singleLine && !readOnly && showCharacterCount) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    text = "${value.length}/50",
                    color = Color.White.copy(alpha = 0.7f),
                    fontFamily = DmSansFontFamily(),
                    fontSize = 12.sp
                )
            }
        },
        isError = error != null,
        modifier = modifier
            .fillMaxWidth()
    )
}