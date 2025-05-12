package org.itsivag.trackmycard.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.LocalPlatformContext
import com.itsivag.helper.OnestFontFamily
import com.itsivag.transactions.data.getTransactionsDatabase
import com.itsivag.transactions.model.TransactionDataModel
import kotlinx.coroutines.launch
import org.itsivag.trackmycard.theme.onBackgroundColor
import org.itsivag.trackmycard.theme.surfaceColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionBottomSheet(setShowBottomSheet: (Boolean) -> Unit, sheetState: SheetState) {
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val context =
        LocalPlatformContext.current
    val dao = remember {
        getTransactionsDatabase(context).transactionsDao()
    }

// Using the DAO to perform operations
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
        TrackMyCardPrimaryButton(text = "Add Transaction") {
            scope.launch {
                setShowBottomSheet(false)
                dao.upsertTransaction(
                    TransactionDataModel(
                        title = title,
                        description = description,
                        amount = 100.0,
                        dateTime = "",
                        category = "",
                        id = 0
                    )
                )
            }
        }
    }

}

