package com.itsivag.cards.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CardMapperDataModel(
    @SerialName("cards")
    val cards: List<Card>
) {
    @Serializable
    data class Card(
        @SerialName("file")
        val `file`: String,
        @SerialName("id")
        val id: String,
        @SerialName("img_url")
        val imgUrl: String,
        @SerialName("name")
        val name: String
    )
}
