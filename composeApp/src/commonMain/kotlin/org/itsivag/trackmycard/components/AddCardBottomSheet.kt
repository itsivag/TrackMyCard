package org.itsivag.trackmycard.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsivag.cards.model.CardDataModel
import com.itsivag.helper.OnestFontFamily
import org.itsivag.trackmycard.theme.onBackgroundColor
import org.itsivag.trackmycard.theme.surfaceColor
import trackmycard.composeapp.generated.resources.Res

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardBottomSheet(
    setAddCardShowBottomSheet: (Boolean) -> Unit,
    sheetState: SheetState,
    upsertCard: (String) -> Unit
) {
    var cardIssuer by rememberSaveable { mutableStateOf("") }
    var cardNetwork by rememberSaveable { mutableStateOf("") }
    var cardName by rememberSaveable { mutableStateOf("") }

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

        MyDropDown(
            label = "Card Issuer",
            options = listOf("First", "Second"),
            value = cardIssuer,
            setOnValueChange = { cardIssuer = it },
            modifier = Modifier
        )

        MyDropDown(
            label = "Card Network",
            options = listOf("Master Card", "Visa", "Rupay"),
            value = cardNetwork,
            setOnValueChange = { cardNetwork = it },
            modifier = Modifier
        )

        MyDropDown(
            label = "Card Name",
            options = listOf("Neo", "My Zone"),
            value = cardName,
            setOnValueChange = { cardName = it },
            modifier = Modifier
        )

        TrackMyCardPrimaryButton(text = "Add Card") {
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
        onExpandedChange = { expanded = it }) {

        TrackMyCardTextInputField(
            label = label,
            value = value,
            readOnly = true,
            modifier = Modifier.menuAnchor(
                MenuAnchorType.PrimaryNotEditable, true
            ).focusRequester(focusRequester)
        ) {}
        ExposedDropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false }) {
            repeat(options.size) {
                DropdownMenuItem(text = { Text(options[it]) }, onClick = {
                    expanded = false
                    setOnValueChange(options[it])
                })
            }
        }
    }
}