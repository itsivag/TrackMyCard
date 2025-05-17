package org.itsivag.trackmycard.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.LocalPlatformContext
import com.itsivag.helper.DmSansFontFamily
import com.itsivag.helper.OnestFontFamily
import com.itsivag.transactions.data.getTransactionsDatabase
import com.itsivag.transactions.model.TransactionDataModel
import com.itsivag.transactions.viewmodel.TransactionsViewModel
import kotlinx.coroutines.launch
import org.itsivag.trackmycard.theme.backgroundColor
import org.itsivag.trackmycard.theme.focusedColor
import org.itsivag.trackmycard.theme.onBackgroundColor
import org.itsivag.trackmycard.theme.primaryColor
import org.itsivag.trackmycard.theme.surfaceColor
import org.koin.compose.viewmodel.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionBottomSheet(
    setShowBottomSheet: (Boolean) -> Unit,
    sheetState: SheetState,
    upsertTransaction: (TransactionDataModel) -> Unit
) {
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf<String?>(null) }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var selectedDate by rememberSaveable { mutableStateOf<Long?>(null) }

    val datePickerState = rememberDatePickerState()
    val scope = rememberCoroutineScope()


    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    selectedDate = datePickerState.selectedDateMillis
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    ModalBottomSheet(
        containerColor = surfaceColor,
        contentColor = onBackgroundColor,
        shape = RoundedCornerShape(12.dp),
        onDismissRequest = {
            setShowBottomSheet(false)
        },
        sheetState = sheetState
    ) {
        Text(
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
            if (value.isEmpty() || value.matches(Regex("^\\d*\\.?\\d*$"))) {
                amount = value
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp).height(48.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            TrackMyCardTextInputField(
                label = "Date",
                value = selectedDate?.let {
                    SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                        .format(Date(it))
                } ?: "",
                readOnly = true,
                modifier = Modifier.weight(1f).fillMaxHeight().padding(end = 8.dp)
            ) {
                showDatePicker = true
            }
            Box(
                modifier = Modifier
                    .padding(start = 8.dp).size(48.dp),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = backgroundColor
                    ),
                    onClick = { showDatePicker = true },
                    modifier = Modifier
                        .border(1.dp, primaryColor, RoundedCornerShape(16.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Select Date",
                        tint = primaryColor,
                    )
                }
            }
        }

        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        TrackMyCardPrimaryButton(text = "Add Transaction") {
            if (title.isBlank()) {
                errorMessage = "Title cannot be empty"
                return@TrackMyCardPrimaryButton
            }

            if (amount.isBlank()) {
                errorMessage = "Amount cannot be empty"
                return@TrackMyCardPrimaryButton
            }

            if (selectedDate == null) {
                errorMessage = "Please select a date"
                return@TrackMyCardPrimaryButton
            }

            val amountValue = amount.toDoubleOrNull()
            if (amountValue == null || amountValue <= 0) {
                errorMessage = "Please enter a valid amount"
                return@TrackMyCardPrimaryButton
            }

            scope.launch {
                try {
                    upsertTransaction(
                        TransactionDataModel(
                            title = title,
                            description = description,
                            amount = amountValue,
                            dateTime = SimpleDateFormat(
                                "yyyy-MM-dd'T'HH:mm:ss",
                                Locale.getDefault()
                            )
                                .format(Date(selectedDate!!)),
                            category = "General",
                            id = 0 // Room will auto-generate this
                        )
                    )
//                    viewModel.upsertTransaction(
//                        TransactionDataModel(
//                            title = title,
//                            description = description,
//                            amount = amountValue,
//                            dateTime = SimpleDateFormat(
//                                "yyyy-MM-dd'T'HH:mm:ss",
//                                Locale.getDefault()
//                            )
//                                .format(Date(selectedDate!!)),
//                            category = "General",
//                            id = 0 // Room will auto-generate this
//                        )
//                    )
                    setShowBottomSheet(false)
                } catch (e: Exception) {
                    errorMessage = "Failed to save transaction: ${e.message}"
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
    readOnly: Boolean = false,
    modifier: Modifier = Modifier,
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
            focusedBorderColor = primaryColor,
            unfocusedBorderColor = Color.Transparent,
            focusedContainerColor = focusedColor,
            unfocusedContainerColor = backgroundColor,
            cursorColor = primaryColor
        ),
        placeholder = { Text("Enter $label") },
        supportingText = {
            if (singleLine && !readOnly) {
                Text(
                    text = "${value.length}/50",
                    color = Color.White.copy(alpha = 0.7f),
                    fontFamily = DmSansFontFamily(),
                    fontSize = 12.sp
                )
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}

