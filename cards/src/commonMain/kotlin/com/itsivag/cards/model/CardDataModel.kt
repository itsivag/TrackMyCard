package com.itsivag.cards.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "cards")
data class CardDataModel(
    @PrimaryKey
    @SerialName("id")
    val id: Int,
    @SerialName("card")
    val card: Card,
    @SerialName("presentation")
    val presentation: Presentation,
    @SerialName("financials")
    val financials: Financials,
    @SerialName("rewards")
    val rewards: Rewards,
    @SerialName("benefits")
    val benefits: List<Benefit>,
    @SerialName("eligibility")
    val eligibility: Eligibility,
    @SerialName("applicationProcess")
    val applicationProcess: ApplicationProcess,
    @SerialName("customerInsights")
    val customerInsights: CustomerInsights,
    @SerialName("customerSupport")
    val customerSupport: CustomerSupport,
    @SerialName("changeLog")
    val changeLog: List<ChangeLog>
) {
    @Serializable
    data class Card(
        @SerialName("cardName")
        val cardName: String,
        @SerialName("cardIssuer")
        val cardIssuer: String,
        @SerialName("ratings")
        val ratings: String,
        @SerialName("categories")
        val categories: List<String>,
        @SerialName("networkType")
        val networkType: String,
        @SerialName("targetAudience")
        val targetAudience: String,
        @SerialName("applyLink")
        val applyLink: String
    )

    @Serializable
    data class Presentation(
        @SerialName("decoration")
        val decoration: Decoration,
        @SerialName("description")
        val description: String,
        @SerialName("highlightFeatures")
        val highlightFeatures: List<String>,
        @SerialName("marketingTagline")
        val marketingTagline: String
    ) {
        @Serializable
        data class Decoration(
            @SerialName("primaryColor")
            val primaryColor: Int,
            @SerialName("secondaryColor")
            val secondaryColor: Int,
            @SerialName("orientation")
            val orientation: String,
            @SerialName("cardImage")
            val cardImage: String,
            @SerialName("material")
            val material: String,
            @SerialName("specialFeatures")
            val specialFeatures: List<String>
        )
    }

    @Serializable
    data class Financials(
        @SerialName("fees")
        val fees: Fees,
        @SerialName("charges")
        val charges: List<Charge>
    ) {
        @Serializable
        data class Fees(
            @SerialName("joining")
            val joining: FeeDetail,
            @SerialName("renewal")
            val renewal: FeeDetail,
            @SerialName("additionalCards")
            val additionalCards: AdditionalCardFee
        ) {
            @Serializable
            data class FeeDetail(
                @SerialName("amount")
                val amount: String,
                @SerialName("tax")
                val tax: String,
                @SerialName("waiverConditions")
                val waiverConditions: List<String>
            )

            @Serializable
            data class AdditionalCardFee(
                @SerialName("amount")
                val amount: String,
                @SerialName("tax")
                val tax: String
            )
        }

        @Serializable
        data class Charge(
            @SerialName("type")
            val type: String,
            @SerialName("value")
            val value: String,
            @SerialName("annualizedValue")
            val annualizedValue: String? = null,
            @SerialName("condition")
            val condition: String? = null,
            @SerialName("details")
            val details: String? = null
        )
    }

    @Serializable
    data class Rewards(
        @SerialName("type")
        val type: String,
        @SerialName("structure")
        val structure: List<RewardStructure>,
        @SerialName("caps")
        val caps: Caps,
        @SerialName("redemptionOptions")
        val redemptionOptions: List<RedemptionOption>,
        @SerialName("excludedCategories")
        val excludedCategories: List<String>
    ) {
        @Serializable
        data class RewardStructure(
            @SerialName("category")
            val category: String,
            @SerialName("rate")
            val rate: String,
            @SerialName("partners")
            val partners: List<String>? = null,
            @SerialName("details")
            val details: String
        )

        @Serializable
        data class Caps(
            @SerialName("overall")
            val overall: String,
            @SerialName("categorySpecific")
            val categorySpecific: List<String> = emptyList()
        )

        @Serializable
        data class RedemptionOption(
            @SerialName("method")
            val method: String,
            @SerialName("description")
            val description: String
        )
    }

    @Serializable
    data class Benefit(
        @SerialName("type")
        val type: String,
        @SerialName("details")
        val details: List<BenefitDetail>,
        @SerialName("totalValue")
        val totalValue: String? = null
    ) {
        @Serializable
        data class BenefitDetail(
            @SerialName("name")
            val name: String,
            @SerialName("value")
            val value: String,
            @SerialName("condition")
            val condition: String,
            @SerialName("expiryPeriod")
            val expiryPeriod: String? = null,
            @SerialName("locations")
            val locations: String? = null,
            @SerialName("programLink")
            val programLink: String? = null
        )
    }

    @Serializable
    data class Eligibility(
        @SerialName("ageRequirement")
        val ageRequirement: AgeRequirement,
        @SerialName("incomeRequirement")
        val incomeRequirement: List<IncomeRequirement>,
        @SerialName("creditScore")
        val creditScore: CreditScore,
        @SerialName("requiredDocuments")
        val requiredDocuments: List<RequiredDocument>,
        @SerialName("residentialStatus")
        val residentialStatus: List<String>
    ) {
        @Serializable
        data class AgeRequirement(
            @SerialName("minimum")
            val minimum: Int,
            @SerialName("maximum")
            val maximum: Int
        )

        @Serializable
        data class IncomeRequirement(
            @SerialName("employmentType")
            val employmentType: String,
            @SerialName("minimumIncome")
            val minimumIncome: String,
            @SerialName("preferredIncomeLevel")
            val preferredIncomeLevel: String
        )

        @Serializable
        data class CreditScore(
            @SerialName("minimum")
            val minimum: String,
            @SerialName("recommended")
            val recommended: String
        )

        @Serializable
        data class RequiredDocument(
            @SerialName("type")
            val type: String,
            @SerialName("options")
            val options: List<String>
        )
    }

    @Serializable
    data class ApplicationProcess(
        @SerialName("channels")
        val channels: List<Channel>,
        @SerialName("processingTime")
        val processingTime: String,
        @SerialName("trackingMethod")
        val trackingMethod: String,
        @SerialName("instantApproval")
        val instantApproval: InstantApproval
    ) {
        @Serializable
        data class Channel(
            @SerialName("type")
            val type: String,
            @SerialName("isAvailable")
            val isAvailable: Boolean,
            @SerialName("url")
            val url: String? = null,
            @SerialName("details")
            val details: String? = null
        )

        @Serializable
        data class InstantApproval(
            @SerialName("isAvailable")
            val isAvailable: Boolean,
            @SerialName("conditions")
            val conditions: List<String>
        )
    }

    @Serializable
    data class CustomerInsights(
        @SerialName("recommendedFor")
        val recommendedFor: List<String>,
        @SerialName("notRecommendedFor")
        val notRecommendedFor: List<String>
    )

    @Serializable
    data class CustomerSupport(
        @SerialName("channels")
        val channels: List<SupportChannel>,
        @SerialName("dedicatedSupport")
        val dedicatedSupport: Boolean
    ) {
        @Serializable
        data class SupportChannel(
            @SerialName("type")
            val type: String,
            @SerialName("value")
            val value: String? = null,
            @SerialName("availability")
            val availability: String? = null,
            @SerialName("responseTime")
            val responseTime: String? = null,
            @SerialName("isAvailable")
            val isAvailable: Boolean? = null
        )
    }

    @Serializable
    data class ChangeLog(
        @SerialName("date")
        val date: String,
        @SerialName("type")
        val type: String,
        @SerialName("change")
        val change: String
    )
} 