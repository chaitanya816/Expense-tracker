package com.example.model

enum class PaymentMethod(val displayName: String) {
    CASH("Cash"),
    CREDIT_CARD("Credit Card"),
    DEBIT_CARD("Debit Card"),
    BANK_TRANSFER("UPI / Bank"),
    ONLINE_WALLET("Online Wallet")
}
