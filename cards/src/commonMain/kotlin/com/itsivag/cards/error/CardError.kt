package com.itsivag.cards.error

sealed class CardError : Throwable() {
    data object CardNotFound : CardError()
    data object CardNameEmpty : CardError()
    data object LimitZero : CardError()
    data object LimitNegative : CardError()
    data object LimitExceedsMaximum : CardError()
    data object CycleEmpty : CardError()
    data object CycleInvalid : CardError()
    data object CardHolderNameEmpty : CardError()
    data class Unknown(override val message: String) : CardError()

    override val message: String
        get() = when (this) {
            is CardNotFound -> "Card not found in mapper"
            is CardNameEmpty -> "Card name cannot be empty"
            is LimitZero -> "Limit cannot be zero"
            is LimitNegative -> "Limit cannot be negative"
            is LimitExceedsMaximum -> "Limit exceeds maximum allowed value"
            is CycleEmpty -> "Cycle date cannot be empty"
            is CycleInvalid -> "Invalid cycle date"
            is CardHolderNameEmpty -> "Card Holder name cannot be empty"
            is Unknown -> message
        }
} 