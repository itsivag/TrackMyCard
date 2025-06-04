package com.itsivag.models.transaction

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TransactionDataModel(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("category")
    val category: String,
    @SerialName("dateTime")
    val dateTime: String,
    @SerialName("amount")
    val amount: Double,
    @SerialName("cardId")
    val cardId: String
)