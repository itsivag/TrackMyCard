package org.itsivag.trackmycard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsivag.helper.DmSansFontFamily
import com.itsivag.helper.OnestFontFamily
import com.itsivag.models.card.CardDataModel
import com.itsivag.models.transaction.TransactionDataModel
import com.itsivag.transactions.viewmodel.UpsertTransactionUIState
import kotlinx.coroutines.launch
import org.itsivag.trackmycard.theme.backgroundColor
import org.itsivag.trackmycard.theme.onBackgroundColor
import org.itsivag.trackmycard.theme.onPrimaryColor
import org.itsivag.trackmycard.theme.primaryColor
import org.itsivag.trackmycard.theme.surfaceColor
import org.itsivag.trackmycard.utils.formatDateTime
import com.itsivag.helper.safeConvertToDouble
import com.itsivag.transactions.error.TransactionError
import org.jetbrains.compose.resources.painterResource
import trackmycard.composeapp.generated.resources.Res
import trackmycard.composeapp.generated.resources.calendar


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun AddTransactionBottomSheet(
    setShowBottomSheet: (Boolean) -> Unit,
    sheetState: SheetState,
    upsertTransaction: (TransactionDataModel) -> Unit,
    currentCard: CardDataModel?,
    upsertTransactionState: UpsertTransactionUIState,
    clearErrorState: () -> Unit
) {
    val scope = rememberCoroutineScope()

    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableStateOf(0.0) }
    var transactionDate by rememberSaveable { mutableStateOf("") }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    var selectedChip by rememberSaveable { mutableStateOf<Int?>(null) }
    val chipList by lazy {
        listOf("General", "Food", "Transport", "Entertainment", "Bills", "Other")
    }

    // Error states for each field
    var titleError by rememberSaveable { mutableStateOf<String?>(null) }
    var descriptionError by rememberSaveable { mutableStateOf<String?>(null) }
    var amountError by rememberSaveable { mutableStateOf<String?>(null) }
    var dateError by rememberSaveable { mutableStateOf<String?>(null) }

    // Clear errors when any field changes
    fun clearErrors() {
        titleError = null
        descriptionError = null
        amountError = null
        dateError = null
        clearErrorState()
    }

    // Reset all states
    fun resetStates() {
        title = ""
        description = ""
        amount = 0.0
        transactionDate = ""
        selectedChip = null
        clearErrors()
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    datePickerState.selectedDateMillis?.let { timestamp ->
                        formatDateTime(timestamp = timestamp, format = "dd-MM-yyyy")?.let {
                            transactionDate = it
                            clearErrors()
                        }
                    }
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
            resetStates()
            setShowBottomSheet(false)
        },
        sheetState = sheetState,
    ) {
        Box {
            Column {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Add Transaction",
                    fontFamily = OnestFontFamily(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    MiniCreditCard(currentCard)
                }

                TrackMyCardTextInputField(
                    label = "Title",
                    value = title,
                    error = titleError,
                    modifier = Modifier.padding(16.dp)
                ) { value ->
                    title = value
                    clearErrors()
                }

                TrackMyCardTextInputField(
                    label = "Description",
                    value = description,
                    error = descriptionError,
                    singleLine = false,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) { value ->
                    description = value
                    clearErrors()
                }

                TrackMyCardTextInputField(
                    label = "Amount",
                    value = if (amount == 0.0) "" else amount.toString(),
                    error = amountError,
                    modifier = Modifier.padding(16.dp)
                ) { value ->
                    amount = value.safeConvertToDouble()
                    clearErrors()
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth().padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TrackMyCardTextInputField(
                        label = "Date",
                        value = transactionDate,
                        error = dateError,
                        readOnly = true,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        showDatePicker = true
                        clearErrors()
                    }

                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = backgroundColor
                        ),
                        onClick = { 
                            showDatePicker = true
                            clearErrors()
                        },
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(48.dp)
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

                LazyRow(modifier = Modifier.padding(16.dp)) {
                    items(chipList.size) {
                        val isSelected = selectedChip == it
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                selectedChip = it
                            },
                            modifier = Modifier.padding(horizontal = 4.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ChipDefaults.filterChipColors(
                                backgroundColor = if (isSelected) primaryColor else backgroundColor,
                            )
                        ) {
                            Text(
                                chipList[it],
                                modifier = Modifier.padding(8.dp),
                                style = TextStyle(
                                    fontFamily = DmSansFontFamily(),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = if (isSelected) onPrimaryColor else Color.White
                            )
                        }
                    }
                }

                TrackMyCardPrimaryButton(
                    text = "Add Transaction",
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    scope.launch {
                        clearErrors()
                        if (currentCard != null) {
                            upsertTransaction(
                                TransactionDataModel(
                                    title = title,
                                    description = description,
                                    amount = amount,
                                    dateTime = transactionDate,
                                    category = chipList[selectedChip ?: 0],
                                    id = 0,
                                    cardId = currentCard.id
                                )
                            )
                        }
                    }
                }
            }

            when (upsertTransactionState) {
                is UpsertTransactionUIState.Error -> {
                    val error = upsertTransactionState.error
                    when (error) {
                        is TransactionError.CardNotFound -> titleError = error.message
                        is TransactionError.TitleEmpty -> titleError = error.message
                        is TransactionError.TitleTooLong -> titleError = error.message
                        is TransactionError.DescriptionTooLong -> descriptionError = error.message
                        is TransactionError.AmountZero -> amountError = error.message
                        is TransactionError.AmountNegative -> amountError = error.message
                        is TransactionError.AmountExceedsLimit -> amountError = error.message
                        is TransactionError.DateEmpty -> dateError = error.message
                        is TransactionError.Unknown -> {
                            // Show general error at the bottom for unexpected errors
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = 48.dp)
                                    .background(
                                        color = surfaceColor,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = error.message,
                                    style = TextStyle(
                                        fontFamily = DmSansFontFamily(),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        }
                    }
                }
                UpsertTransactionUIState.Loading -> {}
                is UpsertTransactionUIState.Success -> {
                    resetStates()
                    setShowBottomSheet(false)
                }
                UpsertTransactionUIState.Idle -> {}
            }
        }
    }
}
