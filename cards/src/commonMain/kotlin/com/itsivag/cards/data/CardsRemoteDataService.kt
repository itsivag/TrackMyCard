package com.itsivag.cards.data

import com.itsivag.cards.model.CardDataModel
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

interface CardsRemoteDataService {
    suspend fun getCard(name: String): CardDataModel
}

class CardsRemoteDataServiceImpl(private val client: HttpClient) : CardsRemoteDataService {
    override suspend fun getCard(name: String): CardDataModel {
        val response =
            client.get("https://raw.githubusercontent.com/itsivag/TrackMyCardPublicData/main/flipkart_axis.json")

        // For text/plain responses, we need to get the text and parse it manually
        val text = response.body<String>()
        val json = Json.decodeFromString<CardDataModel>(text)
        return json
    }
}

