package com.itsivag.transactions.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Entity(tableName = "transactions")
@Serializable
data class TransactionDataModel(
    @SerialName("id")
    @PrimaryKey(autoGenerate = true)
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