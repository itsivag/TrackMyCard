package com.itsivag.cards.data.remote

import com.itsivag.cards.model.CardDataModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.json.Json

interface CardsRemoteDataService {
    suspend fun getCardByName(name: String): CardDataModel
}

class CardsRemoteDataServiceImpl(private val client: HttpClient) : CardsRemoteDataService {
    override suspend fun getCardByName(name: String): CardDataModel {
        val response =
            client.get("https://raw.githubusercontent.com/itsivag/TrackMyCardPublicData/refs/heads/main/flipkart_axis.json")

        // For text/plain responses, we need to get the text and parse it manually
        val text = response.body<String>()
        val json = Json.decodeFromString<CardDataModel>(text)
        return json
    }
}

