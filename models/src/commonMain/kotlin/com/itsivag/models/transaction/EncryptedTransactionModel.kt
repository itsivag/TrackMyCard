package com.itsivag.models.transaction

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.itsivag.models.card.CardDataModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(
    tableName = "transactions",
    foreignKeys = [ForeignKey(
        entity = CardDataModel::class,
        parentColumns = ["id"],
        childColumns = ["cardId"]
    )], indices = [Index("cardId")]
)
@Serializable
data class EncryptedTransactionModel(
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
    val amount: String,
    @SerialName("cardId")
    val cardId: String
)


