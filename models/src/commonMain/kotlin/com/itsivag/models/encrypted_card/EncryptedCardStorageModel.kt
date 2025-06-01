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
    tableName = "encrypted_cards_storage",
    foreignKeys = [
        ForeignKey(
            entity = CardDataModel::class,
            parentColumns = ["id"],
            childColumns = ["id"]
        ),
    ], indices = [Index("id")]
)
data class EncryptedCardStorageModel(
    @PrimaryKey
    @SerialName("id")
    val id: String,
    @SerialName("limit")
    val limit: String,  // Encrypted Double stored as String
    @SerialName("cycle")
    val cycle: String,  // Encrypted Int stored as String
    @SerialName("card_holder_name")
    val cardHolderName: String  // Already a String
) 