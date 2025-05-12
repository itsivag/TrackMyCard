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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.itsivag.transactions.data.TransactionsDao
import com.itsivag.transactions.data.TransactionsDatabase
import com.itsivag.transactions.model.TransactionDataModel
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.itsivag.trackmycard.theme.backgroundColor
import org.itsivag.trackmycard.theme.focusedColor
import org.itsivag.trackmycard.theme.onBackgroundColor
import org.itsivag.trackmycard.theme.primaryColor
import org.itsivag.trackmycard.theme.surfaceColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionBottomSheet(
    setShowBottomSheet: (Boolean) -> Unit,
    sheetState: SheetState
) {
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableStateOf("") }
    
    val scope = rememberCoroutineScope()
    val transactionsDao = remember { TransactionsDatabase.getInstance().transactionsDao() }

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
        TrackMyCardTextInputField(
            label = "Amount",
            value = amount
        ) { value -> 
            // Only allow numbers and decimal point
            if (value.isEmpty() || value.matches(Regex("^\\d*\\.?\\d*$"))) {
                amount = value
            }
        }
        TrackMyCardPrimaryButton(text = "Add Transaction") {
            if (title.isNotBlank() && amount.isNotBlank()) {
                scope.launch {
                    val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    val transaction = TransactionDataModel(
                        id = 0, // Room will auto-generate this
                        title = title,
                        description = description,
                        category = "General", // Default category
                        dateTime = currentDateTime.toString(),
                        amount = amount.toDoubleOrNull() ?: 0.0
                    )
                    transactionsDao.upsertTransaction(transaction)
                    setShowBottomSheet(false)
                }
            }
        }
    }
}

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
            if (singleLine && newValue.length <= 50) {
                onValueChange(newValue)
            } else if (!singleLine) {
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
            if (singleLine) {
                Text(
                    text = "${value.length}/50",
                    color = Color.White.copy(alpha = 0.7f),
                    fontFamily = DmSansFontFamily(),
                    fontSize = 12.sp
                )
            }
        },
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    )
} 