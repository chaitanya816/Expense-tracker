package com.example.data

import com.example.model.PaymentMethod
import com.example.model.TransactionType
import java.util.concurrent.TimeUnit

object SampleData {
    fun generateInitialTransactions(): List<TransactionEntity> {
        val now = System.currentTimeMillis()
        val day = TimeUnit.DAYS.toMillis(1)

        return listOf(
            TransactionEntity(
                title = "Monthly Salary",
                amount = 3850.00,
                type = TransactionType.INCOME.name,
                categoryId = "salary",
                paymentMethod = PaymentMethod.BANK_TRANSFER.displayName,
                timestamp = now - (day * 4),
                note = "Direct payroll deposit"
            ),
            TransactionEntity(
                title = "Whole Foods Groceries",
                amount = 142.60,
                type = TransactionType.EXPENSE.name,
                categoryId = "food",
                paymentMethod = PaymentMethod.DEBIT_CARD.displayName,
                timestamp = now - (day * 3) - 3600000 * 2,
                note = "Weekly meal prep supplies"
            ),
            TransactionEntity(
                title = "Electric & Water Utility",
                amount = 89.40,
                type = TransactionType.EXPENSE.name,
                categoryId = "bills",
                paymentMethod = PaymentMethod.BANK_TRANSFER.displayName,
                timestamp = now - (day * 3),
                note = "Apartment utilities for October"
            ),
            TransactionEntity(
                title = "Subway Metro Pass",
                amount = 45.00,
                type = TransactionType.EXPENSE.name,
                categoryId = "transport",
                paymentMethod = PaymentMethod.CREDIT_CARD.displayName,
                timestamp = now - (day * 2) - 3600000 * 4,
                note = "Monthly transit reload"
            ),
            TransactionEntity(
                title = "Freelance UI Design",
                amount = 650.00,
                type = TransactionType.INCOME.name,
                categoryId = "freelance",
                paymentMethod = PaymentMethod.BANK_TRANSFER.displayName,
                timestamp = now - (day * 2),
                note = "Mobile app wireframes milestone"
            ),
            TransactionEntity(
                title = "Artisan Cafe & Bakery",
                amount = 18.50,
                type = TransactionType.EXPENSE.name,
                categoryId = "food",
                paymentMethod = PaymentMethod.CREDIT_CARD.displayName,
                timestamp = now - day - 3600000 * 3,
                note = "Cold brew & pastry with study group"
            ),
            TransactionEntity(
                title = "Movie Night IMAX Tickets",
                amount = 36.00,
                type = TransactionType.EXPENSE.name,
                categoryId = "entertainment",
                paymentMethod = PaymentMethod.ONLINE_WALLET.displayName,
                timestamp = now - day,
                note = "Weekend cinema with roommates"
            ),
            TransactionEntity(
                title = "Running Shoes & Gear",
                amount = 119.99,
                type = TransactionType.EXPENSE.name,
                categoryId = "shopping",
                paymentMethod = PaymentMethod.CREDIT_CARD.displayName,
                timestamp = now - 3600000 * 6,
                note = "Nike trail runners on discount"
            ),
            TransactionEntity(
                title = "Pharmacy & Vitamins",
                amount = 28.75,
                type = TransactionType.EXPENSE.name,
                categoryId = "health",
                paymentMethod = PaymentMethod.CASH.displayName,
                timestamp = now - 3600000 * 2,
                note = "Cold medicine and electrolyte tabs"
            )
        )
    }
}
