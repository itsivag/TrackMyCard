package com.itsivag.cards.data.remote

import com.itsivag.helper.BASE_URL_WITH_COUNTRY_CODE
import com.itsivag.helper.MAPPER_URL
import com.itsivag.models.card.CardDataModel
import com.itsivag.models.card.CardMapperDataModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.json.Json

interface CardsRemoteDataService {
    suspend fun getCardMapper(): CardMapperDataModel
    suspend fun getCarByPath(path: String): CardDataModel
}

class CardsRemoteDataServiceImpl(private val client: HttpClient) : CardsRemoteDataService {

    override suspend fun getCardMapper(): CardMapperDataModel {
        val response =
            client.get(MAPPER_URL)

        val text = response.body<String>()
        val json = Json.decodeFromString<CardMapperDataModel>(text)
        return json
    }

    override suspend fun getCarByPath(path: String): CardDataModel {
        val response = client.get("$BASE_URL_WITH_COUNTRY_CODE/$path")
        val text = response.body<String>()
        val json = Json.decodeFromString<CardDataModel>(text)
        return json
    }
}

