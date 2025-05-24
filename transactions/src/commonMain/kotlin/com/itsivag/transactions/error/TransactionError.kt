package com.itsivag.transactions.error

sealed class TransactionError : Throwable() {
    data object CardNotFound : TransactionError()
    data object TitleEmpty : TransactionError()
    data class TitleTooLong(val maxLength: Int) : TransactionError()
    data class DescriptionTooLong(val maxLength: Int) : TransactionError()
    data object AmountZero : TransactionError()
    data object AmountNegative : TransactionError()
    data object AmountExceedsLimit : TransactionError()
    data object DateEmpty : TransactionError()
    data class Unknown(override val message: String) : TransactionError()

    override val message: String
        get() = when (this) {
            is CardNotFound -> "Card not found"
            is TitleEmpty -> "Title cannot be empty"
            is TitleTooLong -> "Title cannot be longer than $maxLength characters"
            is DescriptionTooLong -> "Description cannot be longer than $maxLength characters"
            is AmountZero -> "Amount cannot be zero"
            is AmountNegative -> "Amount cannot be negative"
            is AmountExceedsLimit -> "Amount exceeds maximum limit"
            is DateEmpty -> "Date cannot be empty"
            is Unknown -> message
        }
} 