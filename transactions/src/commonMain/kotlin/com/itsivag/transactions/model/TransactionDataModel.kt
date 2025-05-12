package com.itsivag.transactions.model

import androidx.room.Entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Entity
@Serializable
data class TransactionDataModel(
    @SerialName("id")
    val id: String,
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
)