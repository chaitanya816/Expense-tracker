package com.example.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class CategoryInfo(
    val id: String,
    val displayName: String,
    val icon: ImageVector,
    val color: Color,
    val defaultType: TransactionType
)

object Categories {
    val FOOD = CategoryInfo("food", "Food & Dining", Icons.Default.Fastfood, Color(0xFFF97316), TransactionType.EXPENSE)
    val SHOPPING = CategoryInfo("shopping", "Shopping", Icons.Default.ShoppingBag, Color(0xFFEC4899), TransactionType.EXPENSE)
    val TRANSPORT = CategoryInfo("transport", "Transport", Icons.Default.Home, Color(0xFF3B82F6), TransactionType.EXPENSE)
    val ENTERTAINMENT = CategoryInfo("entertainment", "Entertainment", Icons.Default.Movie, Color(0xFF8B5CF6), TransactionType.EXPENSE)
    val BILLS = CategoryInfo("bills", "Bills & Utilities", Icons.Default.Receipt, Color(0xFFEF4444), TransactionType.EXPENSE)
    val HEALTH = CategoryInfo("health", "Health & Medical", Icons.Default.LocalHospital, Color(0xFF10B981), TransactionType.EXPENSE)
    val EDUCATION = CategoryInfo("education", "Education", Icons.Default.School, Color(0xFF06B6D4), TransactionType.EXPENSE)
    val FITNESS = CategoryInfo("fitness", "Fitness & Gym", Icons.Default.FitnessCenter, Color(0xFFEAB308), TransactionType.EXPENSE)
    val OTHER_EXPENSE = CategoryInfo("other_expense", "Other Expense", Icons.Default.CardGiftcard, Color(0xFF64748B), TransactionType.EXPENSE)

    val SALARY = CategoryInfo("salary", "Salary & Wages", Icons.Default.Payments, Color(0xFF10B981), TransactionType.INCOME)
    val FREELANCE = CategoryInfo("freelance", "Freelance / Side Gig", Icons.Default.Work, Color(0xFF14B8A6), TransactionType.INCOME)
    val INVESTMENT = CategoryInfo("investment", "Investment / Dividends", Icons.Default.TrendingUp, Color(0xFF6366F1), TransactionType.INCOME)
    val OTHER_INCOME = CategoryInfo("other_income", "Other Income", Icons.Default.AccountBalance, Color(0xFF8B5CF6), TransactionType.INCOME)

    val allCategories = listOf(
        FOOD, SHOPPING, TRANSPORT, ENTERTAINMENT, BILLS, HEALTH, EDUCATION, FITNESS, OTHER_EXPENSE,
        SALARY, FREELANCE, INVESTMENT, OTHER_INCOME
    )

    fun forType(type: TransactionType): List<CategoryInfo> {
        return allCategories.filter { it.defaultType == type }
    }

    fun findById(id: String): CategoryInfo {
        return allCategories.find { it.id.equals(id, ignoreCase = true) || it.displayName.equals(id, ignoreCase = true) }
            ?: FOOD
    }
}
