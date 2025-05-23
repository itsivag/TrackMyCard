package org.itsivag.trackmycard.transactions

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.LocalPlatformContext
import com.itsivag.helper.OnestFontFamily
import org.itsivag.trackmycard.components.TransactionListItem

@Composable
internal fun TransactionsScreen(padding: PaddingValues) {
    val context = LocalPlatformContext.current


    LazyColumn(modifier = Modifier.padding(padding)) {
        item {
            Text(
                text = "All Transactions",
                fontFamily = OnestFontFamily(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
//        items(transactions.size) { index ->
//            val transaction = transactions[index]
//            TransactionListItem(
//                title = transaction.title,
//                description = transaction.description,
//                amount = transaction.amount,
//                dateTime = transaction.dateTime
//            )
//        }
    }
}