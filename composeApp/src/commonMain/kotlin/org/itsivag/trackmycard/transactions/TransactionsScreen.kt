package org.itsivag.trackmycard.transactions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.LocalPlatformContext
import com.itsivag.helper.OnestFontFamily
import com.itsivag.transactions.viewmodel.TransactionsViewModel
import com.itsivag.transactions.viewmodel.UIState
import org.itsivag.trackmycard.components.TransactionListItem
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import trackmycard.composeapp.generated.resources.Res

@Composable
internal fun TransactionsScreen(
    padding: PaddingValues,
    navigateBack: () -> Unit,
    viewModel: TransactionsViewModel = koinViewModel<TransactionsViewModel>()
) {
    val context = LocalPlatformContext.current
    val transactions = viewModel.transactionState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.statusBarsPadding().padding(padding)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
            IconButton(onClick = navigateBack) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back")
            }
            Text(
                text = "All Transactions",
                fontFamily = OnestFontFamily(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        when (transactions.value) {
            is UIState.Error -> {
                Text("Error fetching transactions")
            }

            UIState.Loading -> {
                CircularProgressIndicator()
            }

            is UIState.Success -> {
                LazyColumn(modifier = Modifier.padding(padding)) {
                    val transactions = (transactions.value as UIState.Success).transactionDataModel
                    item {

                    }
                    items(transactions?.size ?: 0) { index ->
                        val transaction = transactions?.get(index)
                        TransactionListItem(
                            title = transaction?.title ?: "",
                            description = transaction?.description ?: "",
                            amount = transaction?.amount ?: 0.0,
                            dateTime = transaction?.dateTime ?: ""
                        )
                    }
                }
            }
        }

    }

}