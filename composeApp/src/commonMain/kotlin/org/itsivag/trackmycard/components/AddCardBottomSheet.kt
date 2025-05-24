package org.itsivag.trackmycard.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsivag.helper.OnestFontFamily
import com.itsivag.helper.safeConvertToInt
import com.itsivag.models.card.CardMapperDataModel
import com.itsivag.models.encrypted_card.EncryptedCardDataModel
import kotlinx.coroutines.launch
import org.itsivag.trackmycard.theme.onBackgroundColor
import org.itsivag.trackmycard.theme.surfaceColor
import org.itsivag.trackmycard.utils.formatDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardBottomSheet(
    setAddCardShowBottomSheet: (Boolean) -> Unit,
    sheetState: SheetState,
    upsertCard: (EncryptedCardDataModel) -> Unit,
    cardMapperList: CardMapperDataModel?
) {


    val scope = rememberCoroutineScope()
    var cardName by rememberSaveable { mutableStateOf("") }
    val availableCardListPair = cardMapperList?.cards?.map { it.id to it.name }
    var limitText by rememberSaveable { mutableStateOf("") }
    val limit: Double = limitText.toDoubleOrNull() ?: 0.0

    var cycle by rememberSaveable { mutableStateOf("") }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    datePickerState.selectedDateMillis?.let { timestamp ->
                        scope.launch {
                            formatDateTime(timestamp = timestamp, format = "dd")?.let {
                                cycle = it
                            }
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
            setAddCardShowBottomSheet(false)
        },
        sheetState = sheetState,
    ) {
        Box(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
            Column {
                Text(
                    text = "Add Card",
                    fontFamily = OnestFontFamily(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                availableCardListPair?.let {
                    TrackMyCardDropDown(
                        label = "Card Name",
                        options = it.map { it.second },
                        value = cardName,
                        setOnValueChange = { cardName = it },
                        modifier = Modifier
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    TrackMyCardTextInputField(
                        label = "Limit",
                        value = limitText,
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        onValueChange = { newInput ->
                            if (newInput.isEmpty() || newInput.toDoubleOrNull() != null) {
                                limitText = newInput
                            }
                        }
                    )

                    TrackMyCardEditTextWithDatePicker(
                        editTextLabel = "Cycle",
                        value = cycle,
                        modifier = Modifier.weight(1.5f).padding(start = 8.dp),
                        setShowDatePicker = { showDatePicker = it })

                }

                TrackMyCardPrimaryButton(
                    text = "Add Card",
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    availableCardListPair?.find { it.second == cardName }?.let {
                        upsertCard(
                            EncryptedCardDataModel(
                                id = it.first,
                                limit = limit,
                                cycle = cycle.safeConvertToInt()
                            )
                        )
                    }
                    setAddCardShowBottomSheet(false)
                }
            }
        }
    }

}


