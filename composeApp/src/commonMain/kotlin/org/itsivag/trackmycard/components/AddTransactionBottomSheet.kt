package org.itsivag.trackmycard.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsivag.helper.DmSansFontFamily
import com.itsivag.helper.OnestFontFamily
import org.itsivag.trackmycard.theme.backgroundColor
import org.itsivag.trackmycard.theme.focusedColor
import org.itsivag.trackmycard.theme.onBackgroundColor
import org.itsivag.trackmycard.theme.primaryColor
import org.itsivag.trackmycard.theme.surfaceColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionBottomSheet(setShowBottomSheet: (Boolean) -> Unit, sheetState: SheetState) {
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }

    ModalBottomSheet(
        containerColor = surfaceColor,
        contentColor = onBackgroundColor,
        shape = RoundedCornerShape(12.dp),
        onDismissRequest = {
            setShowBottomSheet(false)
        },
        sheetState = sheetState
    ) {
        androidx.compose.material.Text(
            text = "Add Transaction",
            fontFamily = OnestFontFamily(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        TrackMyCardTextInputField(label = "Title", value = title) { value -> title = value }
        TrackMyCardTextInputField(
            label = "Description",
            value = description,
            singleLine = false
        ) { value -> description = value }
        TrackMyCardPrimaryButton(text = "Add Transaction") {
            setShowBottomSheet(false)
        }
    }

}

