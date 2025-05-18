package org.itsivag.trackmycard.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsivag.cards.model.CardMapperDataModel
import com.itsivag.helper.OnestFontFamily
import org.itsivag.trackmycard.theme.backgroundColor
import org.itsivag.trackmycard.theme.onBackgroundColor
import org.itsivag.trackmycard.theme.primaryColor
import org.itsivag.trackmycard.theme.surfaceColor
import org.jetbrains.compose.resources.painterResource
import trackmycard.composeapp.generated.resources.Res
import trackmycard.composeapp.generated.resources.calendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardBottomSheet(
    setAddCardShowBottomSheet: (Boolean) -> Unit,
    sheetState: SheetState,
    upsertCard: (String) -> Unit,
    cardMapperList: CardMapperDataModel?
) {
    var cardName by rememberSaveable { mutableStateOf("") }
    val availableCards = cardMapperList?.cards?.map { it.name }
    var limitText by rememberSaveable { mutableStateOf("") }
    val limit: Double = limitText.toDoubleOrNull() ?: 0.0

    // Store formatted date string directly
    var formattedDate by rememberSaveable { mutableStateOf("") }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    // Format the date when user confirms selection
                    datePickerState.selectedDateMillis?.let { timestamp ->
                        formattedDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                            .format(Date(timestamp))
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
        sheetState = sheetState
    ) {
        Text(
            text = "Add Card",
            fontFamily = OnestFontFamily(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        availableCards?.let {
            MyDropDown(
                label = "Card Name",
                options = it,
                value = cardName,
                setOnValueChange = { cardName = it },
                modifier = Modifier
            )
        }

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
            TrackMyCardTextInputField(
                label = "Limit",
                value = limitText,
                modifier = Modifier.weight(1f)
//                    .padding(end = 8.dp)
                ,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onValueChange = { newInput ->
                    if (newInput.isEmpty() || newInput.toDoubleOrNull() != null) {
                        limitText = newInput
                    }
                }
            )

            Box(
                modifier = Modifier
                    .weight(1f)
//                    .padding(start = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TrackMyCardTextInputField(
                        label = "Expiry",
                        value = formattedDate,
                        readOnly = true,
                        modifier = Modifier
                            .weight(1f),
                        onValueChange = { /* Read-only field */ }
                    )

                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(containerColor = backgroundColor),
                        onClick = { showDatePicker = true },
                        modifier = Modifier
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
            }
        }

        TrackMyCardPrimaryButton(
            text = "Add Card",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            // Pass both card name and limit to your upsert function if needed
            upsertCard(cardName)
            setAddCardShowBottomSheet(false)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDropDown(
    label: String,
    options: List<String>,
    value: String,
    setOnValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    ExposedDropdownMenuBox(
        modifier = modifier.padding(16.dp),
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        TrackMyCardTextInputField(
            label = label,
            value = value,
            readOnly = true,
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                .focusRequester(focusRequester),
            onValueChange = { /* Read-only field */ }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            repeat(options.size) {
                DropdownMenuItem(
                    text = { Text(options[it]) },
                    onClick = {
                        expanded = false
                        setOnValueChange(options[it])
                    }
                )
            }
        }
    }
}