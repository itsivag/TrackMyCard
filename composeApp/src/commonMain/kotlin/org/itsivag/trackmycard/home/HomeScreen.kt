package org.itsivag.trackmycard.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsivag.helper.DmSansFontFamily
import com.itsivag.helper.OnestFontFamily
import org.itsivag.trackmycard.components.CardPager
import org.itsivag.trackmycard.components.TransactionListItem
import org.itsivag.trackmycard.theme.onBackgroundColor
import org.itsivag.trackmycard.theme.primaryColor
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.itsivag.cards.viewmodel.CardsViewModel
import com.itsivag.cards.viewmodel.EncryptedCardUIState
import com.itsivag.cards.viewmodel.UserCreatedCardUIState
import com.itsivag.models.card.CardDataModel
import com.itsivag.transactions.viewmodel.TransactionsViewModel
import com.itsivag.transactions.viewmodel.UIState
import dev.chrisbanes.haze.rememberHazeState
import org.itsivag.trackmycard.components.AddCardBottomSheet
import org.itsivag.trackmycard.components.AddTransactionBottomSheet
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    paddingValues: PaddingValues,
    navigateToTransactionsScreen: () -> Unit,
    transactionViewModel: TransactionsViewModel = koinViewModel<TransactionsViewModel>(),
    cardViewModel: CardsViewModel = koinViewModel<CardsViewModel>()
) {
    val addTransactionSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }

    val addCardSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var addCardShowBottomSheet by remember { mutableStateOf(false) }

    val cards by cardViewModel.cardState.collectAsStateWithLifecycle()
    val cardMapper by cardViewModel.cardMapperState.collectAsStateWithLifecycle()
    val transactions by transactionViewModel.transactionStateWithCardFilter.collectAsStateWithLifecycle()
    val upsertTransactionState by transactionViewModel.upsertTransactionState.collectAsStateWithLifecycle()
    val upsertCardState by cardViewModel.upsertCardState.collectAsStateWithLifecycle()
    val encryptedCardData by cardViewModel.encryptedCardState.collectAsStateWithLifecycle()
    val utilisedLimitState by transactionViewModel.utilisedLimitState.collectAsStateWithLifecycle()
    var currentCard by remember { mutableStateOf<CardDataModel?>(null) }


    LaunchedEffect(currentCard) {
        with(transactionViewModel) {
            getTransactionsWithCardFilter(currentCard?.id)
            getUtilisedLimit(currentCard?.id)
        }
    }

    LaunchedEffect(cards) {
        if (cards is UserCreatedCardUIState.Success) {
            val cardList =
                (cards as UserCreatedCardUIState.Success).cardDataModel
            if (cardList?.isNotEmpty() == true && currentCard == null) {
                currentCard = cardList.firstOrNull()
            }
        }
    }

    if (showBottomSheet) {
        AddTransactionBottomSheet(
            setShowBottomSheet = { showBottomSheet = it },
            sheetState = addTransactionSheetState,
            upsertTransaction = { transactionViewModel.upsertTransaction(it) },
            currentCard = currentCard,
            upsertTransactionState = upsertTransactionState,
            clearErrorState = { transactionViewModel.clearErrorState() }
        )
    }

    if (addCardShowBottomSheet) {
        AddCardBottomSheet(
            setAddCardShowBottomSheet = { addCardShowBottomSheet = it },
            sheetState = addCardSheetState,
            upsertCard = { cardViewModel.upsertCard(it) },
            cardMapperList = cardMapper,
            upsertCardState = upsertCardState,
            clearErrorState = { cardViewModel.clearErrorState() }
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
                when (cards) {
                    is UserCreatedCardUIState.Error -> {
                        Text("Error getting your cards!")
                    }

                    UserCreatedCardUIState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is UserCreatedCardUIState.Success -> {
                        val card =
                            (cards as UserCreatedCardUIState.Success).cardDataModel


                        when (encryptedCardData) {
                            is EncryptedCardUIState.Error -> {}
                            EncryptedCardUIState.Idle -> {}
                            EncryptedCardUIState.Loading -> {}
                            is EncryptedCardUIState.Success -> {
                                CardPager(
                                    encryptedCardDataModelList = (encryptedCardData as EncryptedCardUIState.Success).encryptedCardDataModelList,
                                    cards = card ?: emptyList(),
                                    modifier = Modifier.padding(bottom = 8.dp),
                                    hazeState = hazeState,
                                    setAddCardShowBottomSheet = { addCardShowBottomSheet = it },
                                    setCurrentCard = { currentCard = it },
                                    utilisedLimit = utilisedLimitState
                                )
                            }
                        }

                    }

                    UserCreatedCardUIState.Idle -> {}
                }
            }
        }
        item {
            Row(modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)) {
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

        when (transactions) {
            is UIState.Error -> {
                item {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("Error Getting your transactions!")
                    }
                }
            }

            UIState.Loading -> {
                item {
                    CircularProgressIndicator()
                }
            }

            is UIState.Success -> {
                val t = (transactions as UIState.Success).transactionDataModel
                items(t?.size?.coerceAtMost(5) ?: 0) { index ->
                    val transaction = t?.get(index)
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



