package com.itsivag.models.encrypted_card

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.itsivag.models.card.CardDataModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "encrypted_cards",
    foreignKeys = [
        ForeignKey(
            entity = CardDataModel::class,
            parentColumns = ["id"],
            childColumns = ["id"]
        ),
    ], indices = [Index("id")]
)
data class EncryptedCardDataModel(
    @PrimaryKey
    @SerialName("id")
    val id: String,
    @SerialName("limit")
    val limit: Double,
    @SerialName("cycle")
    val cycle: Int
)