package org.itsivag.trackmycard.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.itsivag.trackmycard.theme.backgroundColor
import org.itsivag.trackmycard.theme.primaryColor
import org.jetbrains.compose.resources.painterResource
import trackmycard.composeapp.generated.resources.Res
import trackmycard.composeapp.generated.resources.calendar

@Composable
fun TrackMyCardEditTextWithDatePicker(
    editTextLabel: String,
    value: String,
    modifier: Modifier = Modifier,
    setShowDatePicker: (Boolean) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TrackMyCardTextInputField(
            label = editTextLabel,
            value = value,
            readOnly = true,
            modifier = Modifier.padding(end = 8.dp)
                .weight(1f),
            onValueChange = {}
        )

        IconButton(
            colors = IconButtonDefaults.iconButtonColors(containerColor = backgroundColor),
            onClick = { setShowDatePicker(true) },
            modifier = Modifier
                .border(1.dp, primaryColor, RoundedCornerShape(16.dp))
        ) {
            Icon(
                painter = painterResource(Res.drawable.calendar),
                contentDescription = "Select Date",
                tint = primaryColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
