package com.itsivag.models.card

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class CardDataModelConverters {
    private val json = Json

    // Card converters
    @TypeConverter
    fun fromCard(card: CardDataModel.Card): String {
        return json.encodeToString(card)
    }

    @TypeConverter
    fun toCard(value: String): CardDataModel.Card {
        return json.decodeFromString(value)
    }

    // Presentation converters
    @TypeConverter
    fun fromPresentation(presentation: CardDataModel.Presentation): String {
        return json.encodeToString(presentation)
    }

    @TypeConverter
    fun toPresentation(value: String): CardDataModel.Presentation {
        return json.decodeFromString(value)
    }

    // Financials converters
    @TypeConverter
    fun fromFinancials(financials: CardDataModel.Financials): String {
        return json.encodeToString(financials)
    }

    @TypeConverter
    fun toFinancials(value: String): CardDataModel.Financials {
        return json.decodeFromString(value)
    }

    // Rewards converters
    @TypeConverter
    fun fromRewards(rewards: CardDataModel.Rewards): String {
        return json.encodeToString(rewards)
    }

    @TypeConverter
    fun toRewards(value: String): CardDataModel.Rewards {
        return json.decodeFromString(value)
    }

    // Benefit list converters
    @TypeConverter
    fun fromBenefitList(benefits: List<CardDataModel.Benefit>): String {
        return json.encodeToString(benefits)
    }

    @TypeConverter
    fun toBenefitList(value: String): List<CardDataModel.Benefit> {
        return json.decodeFromString(value)
    }

    // Eligibility converters
    @TypeConverter
    fun fromEligibility(eligibility: CardDataModel.Eligibility): String {
        return json.encodeToString(eligibility)
    }

    @TypeConverter
    fun toEligibility(value: String): CardDataModel.Eligibility {
        return json.decodeFromString(value)
    }

    // ApplicationProcess converters
    @TypeConverter
    fun fromApplicationProcess(applicationProcess: CardDataModel.ApplicationProcess): String {
        return json.encodeToString(applicationProcess)
    }

    @TypeConverter
    fun toApplicationProcess(value: String): CardDataModel.ApplicationProcess {
        return json.decodeFromString(value)
    }

    // CustomerInsights converters
    @TypeConverter
    fun fromCustomerInsights(customerInsights: CardDataModel.CustomerInsights): String {
        return json.encodeToString(customerInsights)
    }

    @TypeConverter
    fun toCustomerInsights(value: String): CardDataModel.CustomerInsights {
        return json.decodeFromString(value)
    }

    // CustomerSupport converters
    @TypeConverter
    fun fromCustomerSupport(customerSupport: CardDataModel.CustomerSupport): String {
        return json.encodeToString(customerSupport)
    }

    @TypeConverter
    fun toCustomerSupport(value: String): CardDataModel.CustomerSupport {
        return json.decodeFromString(value)
    }

    // ChangeLog list converters
    @TypeConverter
    fun fromChangeLogList(changeLogs: List<CardDataModel.ChangeLog>): String {
        return json.encodeToString(changeLogs)
    }

    @TypeConverter
    fun toChangeLogList(value: String): List<CardDataModel.ChangeLog> {
        return json.decodeFromString(value)
    }
}