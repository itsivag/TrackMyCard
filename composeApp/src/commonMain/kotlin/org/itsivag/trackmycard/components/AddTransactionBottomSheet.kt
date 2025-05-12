package org.itsivag.trackmycard.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.itsivag.trackmycard.theme.backgroundColor
import org.itsivag.trackmycard.theme.onBackgroundColor
import org.itsivag.trackmycard.theme.surfaceColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionBottomSheet(setShowBottomSheet: (Boolean) -> Unit, sheetState: SheetState) {
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        containerColor = surfaceColor,
        contentColor = onBackgroundColor,
        shape = RoundedCornerShape(12.dp),
        onDismissRequest = {
            setShowBottomSheet(false)
        },
        sheetState = sheetState
    ) {
        TrackMyCardTextInputField()
    }
//        Button(onClick = {
//            scope.launch { sheetState.hide() }.invokeOnCompletion {
//                if (!sheetState.isVisible) {
//                    setShowBottomSheet(false)
//                }
//            }
//        }) {
//            Text("Hide bottom sheet")
//        }
}

@Composable
internal fun TrackMyCardTextInputField() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        label = { Text("Title") },
        colors = OutlinedTextFieldDefaults.colors(),
        placeholder = { Text("Enter title") },
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    )
}
