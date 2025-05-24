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
    upsertTransactionState: UpsertTransactionUIState
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

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    datePickerState.selectedDateMillis?.let { timestamp ->
                        formatDateTime(timestamp = timestamp, format = "dd-MM-yyyy")?.let {
                            transactionDate = it
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
                    modifier = Modifier.padding(16.dp)
                ) { value ->
                    title = value
                }

                TrackMyCardTextInputField(
                    label = "Description",
                    value = description,
                    singleLine = false,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) { value ->
                    description = value
                }

                TrackMyCardTextInputField(
                    label = "Amount",
                    value = if (amount == 0.0) "" else amount.toString(),
                    modifier = Modifier.padding(16.dp)
                ) { value ->
                    amount = value.safeConvertToDouble()
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth().padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TrackMyCardTextInputField(
                        label = "Date",
                        value = transactionDate,
                        readOnly = true,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        showDatePicker = true
                    }

                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = backgroundColor
                        ),
                        onClick = { showDatePicker = true },
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
                        currentCard?.let {
                            upsertTransaction(
                                TransactionDataModel(
                                    title = title,
                                    description = description,
                                    amount = amount,
                                    dateTime = transactionDate,
                                    category = "General",
                                    id = 0,
                                    cardId = it.id
                                )
                            )
                        }
//                setShowBottomSheet(false)
                    }
                }
            }
            when (upsertTransactionState) {
                is UpsertTransactionUIState.Error -> {
                    val error = upsertTransactionState.message
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
                            text = error,
                            style = TextStyle(
                                fontFamily = DmSansFontFamily(),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }

                UpsertTransactionUIState.Loading -> {}
                is UpsertTransactionUIState.Success -> {
                    setShowBottomSheet(false)
                }
            }

        }

    }
}
