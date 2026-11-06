package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TransactionEntity
import com.example.model.Categories
import com.example.model.TransactionType
import com.example.ui.components.CategoryBreakdownCard
import com.example.ui.theme.ExpenseRed
import com.example.ui.theme.IncomeGreen
import java.util.Locale

@Composable
fun AnalyticsScreen(
    transactions: List<TransactionEntity>,
    monthlyBudget: Double,
    modifier: Modifier = Modifier
) {
    val totalIncome = transactions.filter { it.type == TransactionType.INCOME.name }.sumOf { it.amount }
    val totalExpense = transactions.filter { it.type == TransactionType.EXPENSE.name }.sumOf { it.amount }
    val savingsRate = if (totalIncome > 0) (((totalIncome - totalExpense) / totalIncome) * 100).toInt().coerceAtLeast(0) else 0

    val healthScore = when {
        totalIncome == 0.0 && totalExpense == 0.0 -> 70
        savingsRate >= 30 && totalExpense <= monthlyBudget -> 94
        savingsRate >= 15 && totalExpense <= monthlyBudget -> 82
        totalExpense <= monthlyBudget -> 68
        else -> 45
    }

    // Payment method distribution
    val paymentDistribution = transactions
        .groupBy { it.paymentMethod }
        .mapValues { (_, list) -> list.sumOf { it.amount } }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("analytics_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Financial Insights",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Analytics computed in real time from Room database",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Financial Health Score Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("health_score_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "$healthScore",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Text(
                                text = "/100",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (healthScore >= 80) "Excellent Financial Health" else if (healthScore >= 60) "Moderate Stability" else "Budget Alert: High Burn Rate",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Estimated savings rate is $savingsRate%. You've spent $${String.format(Locale.US, "%.0f", totalExpense)} of your $${String.format(Locale.US, "%.0f", monthlyBudget)} budget cap.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Income vs Expense comparison bar
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Text(
                        text = "Cash Flow Ratio",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Total Inflow", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(String.format(Locale.US, "$%.2f", totalIncome), style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = IncomeGreen)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Total Outflow", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(String.format(Locale.US, "$%.2f", totalExpense), style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = ExpenseRed)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    val totalFlow = (totalIncome + totalExpense).toFloat()
                    val incomeFraction = if (totalFlow > 0f) (totalIncome.toFloat() / totalFlow) else 0.5f

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(incomeFraction.coerceAtLeast(0.02f))
                                .fillMaxSize()
                                .background(IncomeGreen)
                        )
                        Box(
                            modifier = Modifier
                                .weight((1f - incomeFraction).coerceAtLeast(0.02f))
                                .fillMaxSize()
                                .background(ExpenseRed)
                        )
                    }
                }
            }
        }

        // Category breakdown
        item {
            CategoryBreakdownCard(transactions = transactions)
        }

        // Payment Method breakdown
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CreditCard, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Payment Channels Used",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (paymentDistribution.isEmpty()) {
                        Text("No payment activity recorded.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    } else {
                        paymentDistribution.forEach { (method, amount) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(method, style = MaterialTheme.typography.bodyMedium)
                                Text(
                                    String.format(Locale.US, "$%.2f", amount),
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Actionable Tip
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(Icons.Default.Lightbulb, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Smart Financial Recommendation",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Maintaining at least 20% savings buffer each month protects against unexpected bills. Set category reminders to keep dining & entertainment below 30% of total outflow.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}
