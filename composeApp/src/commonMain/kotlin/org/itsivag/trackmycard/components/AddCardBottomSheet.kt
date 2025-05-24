package org.itsivag.trackmycard.components

import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsivag.cards.error.CardError
import com.itsivag.cards.viewmodel.UpsertCardUIState
import com.itsivag.helper.DmSansFontFamily
import com.itsivag.helper.OnestFontFamily
import com.itsivag.helper.safeConvertToInt
import com.itsivag.models.card.CardMapperDataModel
import com.itsivag.models.encrypted_card.EncryptedCardDataModel
import io.github.aakira.napier.Napier
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
    cardMapperList: CardMapperDataModel?,
    upsertCardState: UpsertCardUIState,
    clearErrorState: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var cardName by rememberSaveable { mutableStateOf("") }
    val availableCardListPair = cardMapperList?.cards?.map { it.id to it.name }
    
    LaunchedEffect(cardMapperList) {
        Napier.v { "Card mapper list: ${cardMapperList?.cards?.map { it.id to it.name }}" }
    }
    
    var limitText by rememberSaveable { mutableStateOf("") }
    val limit: Double = limitText.toDoubleOrNull() ?: 0.0

    var cycle by rememberSaveable { mutableStateOf("") }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    val selectedCardId = remember(cardName, availableCardListPair) {
        availableCardListPair?.find { it.second == cardName }?.first
    }

    LaunchedEffect(cardName) {
        Napier.v { "Selected card name: $cardName" }
        Napier.v { "Available card list: $availableCardListPair" }
        Napier.v { "Found card ID: $selectedCardId" }
    }
    
    // Error states for each field
    var cardNameError by rememberSaveable { mutableStateOf<String?>(null) }
    var limitError by rememberSaveable { mutableStateOf<String?>(null) }
    var cycleError by rememberSaveable { mutableStateOf<String?>(null) }

    // Clear errors when any field changes
    fun clearErrors() {
        cardNameError = null
        limitError = null
        cycleError = null
        clearErrorState()
    }

    // Reset all states
    fun resetStates() {
        cardName = ""
        limitText = ""
        cycle = ""
        clearErrors()
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    datePickerState.selectedDateMillis?.let { timestamp ->
                        scope.launch {
                            formatDateTime(timestamp = timestamp, format = "dd")?.let {
                                cycle = it
                                clearErrors()
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
            resetStates()
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
                        error = cardNameError,
                        setOnValueChange = {
                            cardName = it
                            clearErrors()
                        },
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
                        error = limitError,
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        onValueChange = { newInput ->
                            if (newInput.isEmpty() || newInput.toDoubleOrNull() != null) {
                                limitText = newInput
                                clearErrors()
                            }
                        }
                    )

                    TrackMyCardEditTextWithDatePicker(
                        editTextLabel = "Cycle",
                        value = cycle,
                        error = cycleError,
                        modifier = Modifier.weight(1.5f).padding(start = 8.dp),
                        setShowDatePicker = {
                            showDatePicker = it
                            clearErrors()
                        }
                    )
                }

                TrackMyCardPrimaryButton(
                    text = "Add Card",
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    scope.launch {
                        clearErrors()
                        when {
                            cardName.isBlank() -> {
                                cardNameError = "Please select a card"
                            }
                            selectedCardId == null -> {
                                cardNameError = "Invalid card selection"
                                Napier.e { "Card ID is null for card name: $cardName" }
                            }
                            else -> {
                                upsertCard(
                                    EncryptedCardDataModel(
                                        id = selectedCardId,
                                        limit = limit,
                                        cycle = cycle.safeConvertToInt()
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        when (upsertCardState) {
            is UpsertCardUIState.Error -> {
                val error = upsertCardState.error
                when (error) {
                    is CardError.CardNotFound -> cardNameError = error.message
                    is CardError.LimitZero -> limitError = error.message
                    is CardError.LimitNegative -> limitError = error.message
                    is CardError.LimitExceedsMaximum -> limitError = error.message
                    is CardError.CycleEmpty -> cycleError = error.message
                    is CardError.CycleInvalid -> cycleError = error.message
                    is CardError.CardNameEmpty -> cardNameError = error.message

                    is CardError.Unknown -> {
                        // Show general error at the bottom for unexpected errors
                        Box(
                            modifier = Modifier
//                                .align(Alignment.BottomCenter)
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

            UpsertCardUIState.Loading -> {}
            is UpsertCardUIState.Success -> {
                resetStates()
                setAddCardShowBottomSheet(false)
            }

            UpsertCardUIState.Idle -> {}
        }
    }
}



