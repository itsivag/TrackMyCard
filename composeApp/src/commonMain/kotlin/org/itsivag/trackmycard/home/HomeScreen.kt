package org.itsivag.trackmycard.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.LocalPlatformContext
import com.itsivag.helper.DmSansFontFamily
import com.itsivag.helper.OnestFontFamily
import org.itsivag.trackmycard.components.AddTransactionBottomSheet
import org.itsivag.trackmycard.components.CardPager
import org.itsivag.trackmycard.components.TransactionListItem
import org.itsivag.trackmycard.theme.onBackgroundColor
import org.itsivag.trackmycard.theme.primaryColor
import androidx.compose.runtime.collectAsState
import com.itsivag.transactions.data.getTransactionsDatabase
import dev.chrisbanes.haze.rememberHazeState
import org.itsivag.trackmycard.components.AddCardBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    paddingValues: PaddingValues,
    navigateToTransactionsScreen: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }


    val addCardSheetState = rememberModalBottomSheetState()
    var addCardShowBottomSheet by remember { mutableStateOf(false) }

    val context = LocalPlatformContext.current
    val dao = remember {
        getTransactionsDatabase(context).transactionsDao()
    }
    val transactions by dao.getAllTransactions().collectAsState(initial = emptyList())

    val config = LocalWindowInfo.current
    val height = rememberSaveable { config.containerSize.height }

    if (showBottomSheet) {
        AddTransactionBottomSheet(
            setShowBottomSheet = { showBottomSheet = it },
            sheetState = sheetState
        )
    }

    if (addCardShowBottomSheet) {
        AddCardBottomSheet(
            setAddCardShowBottomSheet = { addCardShowBottomSheet = it },
            sheetState = addCardSheetState
        )
    }
    val hazeState = rememberHazeState()
    LazyColumn(modifier = Modifier.padding(paddingValues)) {

        item {
            Box {
//                AsyncImage(
//                    model = ImageRequest.Builder(LocalPlatformContext.current)
//                        .data("https://raw.githubusercontent.com/itsivag/TrackMyCardPublicData/main/sample.webp")
//                        .crossfade(true)
//                        .build(),
//                    contentDescription = "WebP Image",
//                    modifier = Modifier.fillMaxWidth().height((height * 0.1).dp)
//                        .hazeSource(hazeState)
//                )
                CardPager(
                    cards = listOf(),
                    modifier = Modifier.padding(bottom = 8.dp),
                    hazeState = hazeState,
                    addCardShowBottomSheet = addCardShowBottomSheet,
                    setAddCardShowBottomSheet = { addCardShowBottomSheet = it }
                )
            }
        }
        item {
            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                OutlinedButton(
                    onClick = {
                        showBottomSheet = true
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = onBackgroundColor)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add Transaction",
                            modifier = Modifier.size(36.dp)
                        )
                        Text(
                            text = "Add Transaction",
                            fontFamily = DmSansFontFamily(),
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
        item {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Transactions",
                    fontFamily = OnestFontFamily(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                )
                Spacer(Modifier.weight(1f))
                TextButton(onClick = navigateToTransactionsScreen) {
                    Text(
                        text = "See All",
                        fontFamily = OnestFontFamily(),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = primaryColor
                    )
                }
            }
        }

        items(transactions.size.coerceAtMost(5)) { index ->
            val transaction = transactions[index]
            TransactionListItem(
                title = transaction.title,
                description = transaction.description,
                amount = transaction.amount,
                dateTime = transaction.dateTime
            )
        }
    }
}



