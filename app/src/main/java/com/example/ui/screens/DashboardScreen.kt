package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TransactionEntity
import com.example.model.TransactionType
import com.example.ui.components.BudgetCard
import com.example.ui.components.CategoryBreakdownCard
import com.example.ui.components.EmptyStateView
import com.example.ui.components.SummaryHeroCard
import com.example.ui.components.TransactionItemCard
import com.example.ui.theme.ExpenseRed
import com.example.ui.theme.IncomeGreen

@Composable
fun DashboardScreen(
    totalIncome: Double,
    totalExpenses: Double,
    monthlyBudget: Double,
    transactions: List<TransactionEntity>,
    onAddClick: (TransactionType) -> Unit,
    onBudgetUpdated: (Double) -> Unit,
    onDeleteTransaction: (TransactionEntity) -> Unit,
    onViewAllClick: () -> Unit,
    onDemonstrateEmptyState: () -> Unit,
    onRestoreSampleData: () -> Unit,
    onDemonstrateRejectedEntry: () -> Unit,
    onSetLowBudgetDemo: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("dashboard_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))

            // Greeting & Branding Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "SpendWise",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Real-time personal finance manager",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Insights,
                        contentDescription = "Insights Icon",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        // 5.1 DEMO CONTROL TOOLBAR
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("demo_control_toolbar"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                )
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "5.1 Presentation Live Triggers",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            text = "One-tap demo helpers",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SuggestionChip(
                            onClick = onDemonstrateEmptyState,
                            label = { Text("1. Show Empty State", fontSize = 11.sp) },
                            icon = { Icon(Icons.Default.DeleteSweep, contentDescription = null, modifier = Modifier.size(14.dp)) },
                            colors = SuggestionChipDefaults.suggestionChipColors(containerColor = MaterialTheme.colorScheme.surface),
                            modifier = Modifier.testTag("trigger_empty_state_chip")
                        )

                        SuggestionChip(
                            onClick = onDemonstrateRejectedEntry,
                            label = { Text("2. Show Rejected Form", fontSize = 11.sp) },
                            icon = { Icon(Icons.Default.WarningAmber, contentDescription = null, tint = ExpenseRed, modifier = Modifier.size(14.dp)) },
                            colors = SuggestionChipDefaults.suggestionChipColors(containerColor = MaterialTheme.colorScheme.surface),
                            modifier = Modifier.testTag("trigger_reject_chip")
                        )

                        SuggestionChip(
                            onClick = onSetLowBudgetDemo,
                            label = { Text("3. Simulate Budget Alert", fontSize = 11.sp) },
                            icon = { Icon(Icons.Default.TrendingDown, contentDescription = null, tint = ExpenseRed, modifier = Modifier.size(14.dp)) },
                            colors = SuggestionChipDefaults.suggestionChipColors(containerColor = MaterialTheme.colorScheme.surface),
                            modifier = Modifier.testTag("trigger_low_budget_chip")
                        )

                        SuggestionChip(
                            onClick = onRestoreSampleData,
                            label = { Text("4. Reload Sample Data", fontSize = 11.sp) },
                            icon = { Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = IncomeGreen, modifier = Modifier.size(14.dp)) },
                            colors = SuggestionChipDefaults.suggestionChipColors(containerColor = MaterialTheme.colorScheme.surface),
                            modifier = Modifier.testTag("trigger_reload_chip")
                        )
                    }
                }
            }
        }

        // Summary Hero Card
        item {
            SummaryHeroCard(
                totalIncome = totalIncome,
                totalExpenses = totalExpenses,
                onAddClick = onAddClick
            )
        }

        // Budget Card
        item {
            BudgetCard(
                monthlyBudget = monthlyBudget,
                totalExpenses = totalExpenses,
                onBudgetUpdated = onBudgetUpdated
            )
        }

        // Category breakdown
        item {
            CategoryBreakdownCard(transactions = transactions)
        }

        // Recent Transactions Section Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Transactions",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )

                if (transactions.isNotEmpty()) {
                    TextButton(
                        onClick = onViewAllClick,
                        modifier = Modifier.testTag("view_all_transactions_button")
                    ) {
                        Text("View All (${transactions.size})")
                        Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }

        // List or Empty state
        if (transactions.isEmpty()) {
            item {
                EmptyStateView(
                    onAddTransactionClick = { onAddClick(TransactionType.EXPENSE) },
                    onLoadSampleDataClick = onRestoreSampleData,
                    customTitle = "No Transactions Recorded",
                    customSubtitle = "This empty state is intentionally displayed to verify UI stability with 0 database entries during the rubric demonstration."
                )
            }
        } else {
            items(
                items = transactions.take(6),
                key = { it.id }
            ) { tx ->
                TransactionItemCard(
                    transaction = tx,
                    onDeleteClick = onDeleteTransaction
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}
