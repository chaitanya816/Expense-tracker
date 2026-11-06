package com.example.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TransactionEntity
import com.example.model.Categories
import com.example.model.TransactionType
import com.example.ui.FilterType
import com.example.ui.components.EmptyStateView
import com.example.ui.components.TransactionItemCard
import java.util.Locale

@Composable
fun TransactionsScreen(
    transactions: List<TransactionEntity>,
    searchQuery: String,
    filterType: FilterType,
    selectedCategory: String?,
    onSearchChange: (String) -> Unit,
    onFilterChange: (FilterType) -> Unit,
    onCategoryChange: (String?) -> Unit,
    onDeleteTransaction: (TransactionEntity) -> Unit,
    onAddTransactionClick: () -> Unit,
    onLoadSampleData: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalSum = transactions.sumOf {
        if (it.type == TransactionType.EXPENSE.name) -it.amount else it.amount
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("transactions_screen")
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Transaction History",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            placeholder = { Text("Search by merchant, note, or payment...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search Icon")
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(
                        onClick = { onSearchChange("") },
                        modifier = Modifier.testTag("clear_search_button")
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear Search")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("search_text_field")
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Filter Pills (All, Expense, Income)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = filterType == FilterType.ALL,
                onClick = { onFilterChange(FilterType.ALL) },
                label = { Text("All") },
                modifier = Modifier.testTag("filter_all_chip")
            )
            FilterChip(
                selected = filterType == FilterType.EXPENSE,
                onClick = { onFilterChange(FilterType.EXPENSE) },
                label = { Text("Expenses Only") },
                modifier = Modifier.testTag("filter_expenses_chip")
            )
            FilterChip(
                selected = filterType == FilterType.INCOME,
                onClick = { onFilterChange(FilterType.INCOME) },
                label = { Text("Income Only") },
                modifier = Modifier.testTag("filter_income_chip")
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Category Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FilterChip(
                selected = selectedCategory == null,
                onClick = { onCategoryChange(null) },
                label = { Text("All Categories", fontSize = 11.sp) },
                modifier = Modifier.testTag("filter_cat_all")
            )

            Categories.allCategories.forEach { cat ->
                val isSelected = selectedCategory.equals(cat.id, ignoreCase = true)
                FilterChip(
                    selected = isSelected,
                    onClick = { onCategoryChange(if (isSelected) null else cat.id) },
                    label = { Text(cat.displayName, fontSize = 11.sp) },
                    leadingIcon = {
                        Icon(
                            imageVector = cat.icon,
                            contentDescription = null,
                            tint = if (isSelected) MaterialTheme.colorScheme.primary else cat.color,
                            modifier = Modifier.size(14.dp)
                        )
                    },
                    modifier = Modifier.testTag("filter_cat_${cat.id}")
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Results summary banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${transactions.size} records found",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = String.format(Locale.US, "Net: %s$%.2f", if (totalSum >= 0) "+" else "-", kotlin.math.abs(totalSum)),
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Transaction list or empty state
        if (transactions.isEmpty()) {
            EmptyStateView(
                onAddTransactionClick = onAddTransactionClick,
                onLoadSampleDataClick = onLoadSampleData,
                customTitle = if (searchQuery.isNotBlank() || selectedCategory != null) "No Matching Transactions" else "No Transactions Recorded",
                customSubtitle = if (searchQuery.isNotBlank() || selectedCategory != null)
                    "No items matched the active search filters. Try adjusting your query or resetting category selection."
                else
                    "Your transaction ledger has zero entries. Tap 'Load Demo Sample Data' to populate test items or add a transaction."
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    items = transactions,
                    key = { it.id }
                ) { tx ->
                    TransactionItemCard(
                        transaction = tx,
                        onDeleteClick = onDeleteTransaction
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}
