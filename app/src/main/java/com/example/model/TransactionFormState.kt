package com.example.model

data class TransactionFormState(
    val title: String = "",
    val amount: String = "",
    val type: TransactionType = TransactionType.EXPENSE,
    val categoryId: String = "food",
    val paymentMethod: PaymentMethod = PaymentMethod.CASH,
    val dateMillis: Long = System.currentTimeMillis(),
    val titleError: String? = null,
    val amountError: String? = null,
    val isRejected: Boolean = false,
    val rejectedReason: String? = null,
    val hasAttemptedSubmission: Boolean = false
) {
    val isValid: Boolean
        get() {
            val validAmount = amount.toDoubleOrNull()?.let { it > 0.0 } ?: false
            val validTitle = title.trim().length >= 2
            return validAmount && validTitle
        }

    fun validate(): TransactionFormState {
        var newTitleErr: String? = null
        var newAmountErr: String? = null
        var reason: String? = null

        val parsedAmount = amount.trim().toDoubleOrNull()
        if (amount.isBlank()) {
            newAmountErr = "Amount is required"
            reason = "Entry rejected: Transaction amount cannot be empty."
        } else if (parsedAmount == null) {
            newAmountErr = "Please enter a valid numeric amount"
            reason = "Entry rejected: Non-numeric characters detected."
        } else if (parsedAmount <= 0.0) {
            newAmountErr = "Amount must be strictly greater than $0.00"
            reason = "Entry rejected: Negative or zero amounts ($amount) are forbidden to preserve ledger consistency."
        } else if (parsedAmount > 10_000_000.0) {
            newAmountErr = "Amount exceeds the transaction safety ceiling ($10M)"
            reason = "Entry rejected: Value exceeds safety ceiling."
        }

        if (title.isBlank()) {
            newTitleErr = "Title / Merchant note is required"
            if (reason == null) reason = "Entry rejected: Description is blank. Every entry requires a title for accounting."
        } else if (title.trim().length < 2) {
            newTitleErr = "Title must be at least 2 characters long"
            if (reason == null) reason = "Entry rejected: Description is too short."
        }

        val rejected = newTitleErr != null || newAmountErr != null
        return copy(
            titleError = newTitleErr,
            amountError = newAmountErr,
            isRejected = rejected,
            rejectedReason = reason,
            hasAttemptedSubmission = true
        )
    }
}
