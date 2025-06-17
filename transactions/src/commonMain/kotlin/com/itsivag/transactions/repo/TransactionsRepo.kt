package com.itsivag.transactions.repo

import com.itsivag.crypto.CryptoHelper
import com.itsivag.models.transaction.EncryptedTransactionModel
import com.itsivag.models.transaction.TransactionDataModel
import com.itsivag.transactions.data.TransactionsLocalDataService
import com.itsivag.transactions.error.TransactionError
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

interface TransactionsRepo {
    suspend fun upsertTransaction(transaction: TransactionDataModel): Result<Boolean>
    suspend fun getTransactions(): Result<Flow<List<TransactionDataModel>>>
    suspend fun getTransactionsWithCardFilter(cardId: String): Result<Flow<List<TransactionDataModel>>>
    suspend fun getUtilisedLimit(cardId: String): Result<Flow<Double>>
}

class TransactionsRepoImpl(
    private val transactionsLocalDataService: TransactionsLocalDataService,
    private val cryptoHelper: CryptoHelper
) :
    TransactionsRepo {

    override suspend fun upsertTransaction(transaction: TransactionDataModel): Result<Boolean> {
        try {
            validateTransaction(transaction)
            with(cryptoHelper) {
                transaction.apply {
                    EncryptedTransactionModel(
                        id = id,
                        title = title,
                        description = description,
                        category = category,
                        dateTime = dateTime,
                        amount = amount.toString(),
                        cardId = cardId
                    ).encryptFields(exclusions = arrayOf("id", "cardId"))
                        .also {
                            transactionsLocalDataService.upsertTransaction(it)
                        }
                }
                return Result.success(true)
            }
        } catch (e: TransactionError) {
            Napier.e("Validation error", e)
            return Result.failure(e)
        } catch (e: Exception) {
            Napier.e("Error upserting transaction", e)
            return Result.failure(TransactionError.Unknown(e.message ?: "Unknown error occurred"))
        }
    }

    override suspend fun getTransactions(): Result<Flow<List<TransactionDataModel>>> {
        return try {
            val res = transactionsLocalDataService.getTransactions()
            val convertedFlow = res.map {
                it.map { encryptedTransactionDataModel ->
                    try {
                        with(cryptoHelper) {
                            encryptedTransactionDataModel.decryptFields(
                                exclusions = arrayOf(
                                    "id",
                                    "cardId"
                                )
                            )
                                .run {
                                    TransactionDataModel(
                                        id = id,
                                        title = title,
                                        description = description,
                                        category = category,
                                        cardId = cardId,
                                        amount = amount.toDouble(),
                                        dateTime = dateTime
                                    )
                                }
                        }
                    } catch (e: Exception) {
                        Napier.e("Error decrypting transaction data ", e)
                        TransactionDataModel(
                            id = -1,
                            title = "",
                            description = "",
                            category = "",
                            dateTime = "",
                            amount = 0.0,
                            cardId = ""
                        )
                    }
                }
            }
            Result.success(convertedFlow)
        } catch (e: Exception) {
            Napier.e("Error getting all transactions", e)
            Result.failure(e)
        }
    }

    override suspend fun getTransactionsWithCardFilter(cardId: String): Result<Flow<List<TransactionDataModel>>> {
        return try {
            if (cardId.isBlank()) {
                throw TransactionError.CardNotFound
            }
            val res = transactionsLocalDataService.getTransactionsWithCardFilter(cardId)
            val convertedFlow = res.map {
                it.map { encryptedTransactionDataModel ->
                    try {
                        with(cryptoHelper) {
                            encryptedTransactionDataModel.decryptFields(
                                exclusions = arrayOf(
                                    "id",
                                    "cardId"
                                )
                            )
                                .run {
                                    TransactionDataModel(
                                        id = id,
                                        title = title,
                                        description = description,
                                        category = category,
                                        cardId = cardId,
                                        amount = amount.toDouble(),
                                        dateTime = dateTime
                                    )
                                }
                        }
                    } catch (e: Exception) {
                        Napier.e("Error decrypting transaction data ", e)
                        TransactionDataModel(
                            id = -1,
                            title = "",
                            description = "",
                            category = "",
                            dateTime = "",
                            amount = 0.0,
                            cardId = ""
                        )
                    }
                }
            }
            Result.success(convertedFlow)
        } catch (e: Exception) {
            Napier.e("Error getting transactions with card filter", e)
            Result.failure(e)
        }
    }

    override suspend fun getUtilisedLimit(cardId: String): Result<Flow<Double>> {
        return try {
            if (cardId.isBlank()) {
                throw TransactionError.CardNotFound
            }
            val res = transactionsLocalDataService.getTransactionsWithCardFilter(cardId)
            val convertedFlow = res.map {
                it.map { encryptedTransactionDataModel ->
                    try {
                        with(cryptoHelper) {
                            encryptedTransactionDataModel.decryptFields(
                                exclusions = arrayOf(
                                    "id",
                                    "cardId"
                                )
                            ).run {
                                TransactionDataModel(
                                    id = id,
                                    title = title,
                                    description = description,
                                    category = category,
                                    cardId = cardId,
                                    amount = amount.toDouble(),
                                    dateTime = dateTime
                                )
                            }
                        }
                    } catch (e: Exception) {
                        Napier.e("Error decrypting transaction data ", e)
                        TransactionDataModel(
                            id = -1,
                            title = "",
                            description = "",
                            category = "",
                            dateTime = "",
                            amount = 0.0,
                            cardId = ""
                        )
                    }
                }.sumOf { it.amount }
            }
            Result.success(convertedFlow)
        } catch (e: Exception) {
            Napier.e("Error getting transactions with card filter", e)
            Result.failure(e)
        }
    }


    private fun validateTransaction(transaction: TransactionDataModel) {
        with(transaction) {
            when {
                cardId.isBlank() -> throw TransactionError.CardNotFound
                title.isBlank() -> throw TransactionError.TitleEmpty
                title.length > 50 -> throw TransactionError.TitleTooLong(50)
                description.length > 100 -> throw TransactionError.DescriptionTooLong(100)
                amount == 0.0 -> throw TransactionError.AmountZero
                amount < 0 -> throw TransactionError.AmountNegative
                amount > 1_000_000_000 -> throw TransactionError.AmountExceedsLimit
                dateTime.isBlank() -> throw TransactionError.DateEmpty
            }
        }
    }
}