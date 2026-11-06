package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val amount: Double,
    val type: String, // "EXPENSE" or "INCOME"
    val categoryId: String,
    val paymentMethod: String,
    val timestamp: Long = System.currentTimeMillis(),
    val note: String = ""
)
