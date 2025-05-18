package com.itsivag.cards.data.remote

import com.itsivag.cards.model.CardDataModel
import com.itsivag.cards.model.CardMapperDataModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.json.Json

interface CardsRemoteDataService {
    suspend fun getCardByName(name: String): CardDataModel
    suspend fun getCardMapper(): CardMapperDataModel
}

class CardsRemoteDataServiceImpl(private val client: HttpClient) : CardsRemoteDataService {
    override suspend fun getCardByName(name: String): CardDataModel {
        val response =
            client.get("https://raw.githubusercontent.com/itsivag/TrackMyCardPublicData/refs/heads/main/flipkart_axis.json")

        val text = response.body<String>()
        val json = Json.decodeFromString<CardDataModel>(text)
        return json
    }

    override suspend fun getCardMapper(): CardMapperDataModel {
        val response =
            client.get("https://raw.githubusercontent.com/itsivag/TrackMyCardPublicData/refs/heads/main/mapper.json")

        val text = response.body<String>()
        val json = Json.decodeFromString<CardMapperDataModel>(text)
        return json
    }
}

