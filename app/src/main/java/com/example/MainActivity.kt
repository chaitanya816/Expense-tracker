package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.PieChart
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.AppDatabase
import com.example.data.ExpenseRepository
import com.example.model.TransactionType
import com.example.ui.ExpenseViewModel
import com.example.ui.screens.AddTransactionSheet
import com.example.ui.screens.AnalyticsScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.DemoGuideScreen
import com.example.ui.screens.TransactionsScreen
import com.example.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: ExpenseViewModel by viewModels {
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = ExpenseRepository(database.transactionDao())
        ExpenseViewModel.Factory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppScreen(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(viewModel: ExpenseViewModel) {
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val transactions by viewModel.allTransactions.collectAsStateWithLifecycle()
    val filteredTransactions by viewModel.filteredTransactions.collectAsStateWithLifecycle()
    val totalIncome by viewModel.totalIncome.collectAsStateWithLifecycle()
    val totalExpenses by viewModel.totalExpenses.collectAsStateWithLifecycle()
    val monthlyBudget by viewModel.monthlyBudget.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val filterType by viewModel.filterType.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategoryId.collectAsStateWithLifecycle()

    val isAddSheetOpen by viewModel.isAddSheetOpen.collectAsStateWithLifecycle()
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val userMessage by viewModel.userMessage.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    // Handle back button when on non-dashboard tabs
    BackHandler(enabled = currentTab != 0) {
        viewModel.setCurrentTab(0)
    }

    // Display user messages via Snackbar
    LaunchedEffect(userMessage) {
        userMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearUserMessage()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("main_scaffold"),
        contentWindowInsets = WindowInsets.safeDrawing,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.testTag("bottom_nav_bar"),
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = currentTab == 0,
                    onClick = { viewModel.setCurrentTab(0) },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == 0) Icons.Filled.Dashboard else Icons.Outlined.Dashboard,
                            contentDescription = "Dashboard"
                        )
                    },
                    label = { Text("Dashboard") },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.testTag("nav_tab_dashboard")
                )

                NavigationBarItem(
                    selected = currentTab == 1,
                    onClick = { viewModel.setCurrentTab(1) },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == 1) Icons.Filled.ReceiptLong else Icons.Outlined.ReceiptLong,
                            contentDescription = "History"
                        )
                    },
                    label = { Text("History") },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.testTag("nav_tab_history")
                )

                NavigationBarItem(
                    selected = currentTab == 2,
                    onClick = { viewModel.setCurrentTab(2) },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == 2) Icons.Filled.PieChart else Icons.Outlined.PieChart,
                            contentDescription = "Analytics"
                        )
                    },
                    label = { Text("Analytics") },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.testTag("nav_tab_analytics")
                )

                NavigationBarItem(
                    selected = currentTab == 3,
                    onClick = { viewModel.setCurrentTab(3) },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == 3) Icons.Filled.Timer else Icons.Outlined.Timer,
                            contentDescription = "5.1 Demo Guide"
                        )
                    },
                    label = { Text("5.1 Demo") },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.testTag("nav_tab_demo_guide")
                )
            }
        },
        floatingActionButton = {
            if (currentTab == 0 || currentTab == 1) {
                FloatingActionButton(
                    onClick = { viewModel.openAddSheet(TransactionType.EXPENSE) },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.testTag("global_fab_add")
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Transaction")
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                0 -> DashboardScreen(
                    totalIncome = totalIncome,
                    totalExpenses = totalExpenses,
                    monthlyBudget = monthlyBudget,
                    transactions = transactions,
                    onAddClick = { type -> viewModel.openAddSheet(type) },
                    onBudgetUpdated = { newBudget -> viewModel.setMonthlyBudget(newBudget) },
                    onDeleteTransaction = { tx -> viewModel.deleteTransaction(tx) },
                    onViewAllClick = { viewModel.setCurrentTab(1) },
                    onDemonstrateEmptyState = { viewModel.demonstrateEmptyState() },
                    onRestoreSampleData = { viewModel.restoreSampleData() },
                    onDemonstrateRejectedEntry = {
                        viewModel.openAddSheet(TransactionType.EXPENSE)
                        viewModel.demonstrateRejectedEntry()
                    },
                    onSetLowBudgetDemo = {
                        // Sets budget below current expenses to simulate amber warning and overbudget state
                        viewModel.setMonthlyBudget(400.0)
                    }
                )

                1 -> TransactionsScreen(
                    transactions = filteredTransactions,
                    searchQuery = searchQuery,
                    filterType = filterType,
                    selectedCategory = selectedCategory,
                    onSearchChange = { viewModel.setSearchQuery(it) },
                    onFilterChange = { viewModel.setFilterType(it) },
                    onCategoryChange = { viewModel.setSelectedCategory(it) },
                    onDeleteTransaction = { tx -> viewModel.deleteTransaction(tx) },
                    onAddTransactionClick = { viewModel.openAddSheet(TransactionType.EXPENSE) },
                    onLoadSampleData = { viewModel.restoreSampleData() }
                )

                2 -> AnalyticsScreen(
                    transactions = transactions,
                    monthlyBudget = monthlyBudget
                )

                3 -> DemoGuideScreen(
                    onDemonstrateEmptyState = {
                        viewModel.demonstrateEmptyState()
                        viewModel.setCurrentTab(0)
                    },
                    onDemonstrateRejectedEntry = {
                        viewModel.openAddSheet(TransactionType.EXPENSE)
                        viewModel.demonstrateRejectedEntry()
                    },
                    onSetLowBudgetDemo = {
                        viewModel.setMonthlyBudget(400.0)
                        viewModel.setCurrentTab(0)
                    },
                    onRestoreSampleData = {
                        viewModel.restoreSampleData()
                        viewModel.setCurrentTab(0)
                    }
                )
            }
        }
    }

    // Modal Add Transaction Sheet
    if (isAddSheetOpen) {
        AddTransactionSheet(
            formState = formState,
            onTitleChange = { viewModel.onTitleChanged(it) },
            onAmountChange = { viewModel.onAmountChanged(it) },
            onTypeChange = { viewModel.onTypeChanged(it) },
            onCategoryChange = { viewModel.onCategoryChanged(it) },
            onPaymentMethodChange = { viewModel.onPaymentMethodChanged(it) },
            onTriggerRejectedEntryDemo = { viewModel.demonstrateRejectedEntry() },
            onSubmit = {
                viewModel.submitTransaction {
                    coroutineScope.launch { sheetState.hide() }
                }
            },
            onDismiss = {
                coroutineScope.launch {
                    sheetState.hide()
                    viewModel.closeAddSheet()
                }
            },
            sheetState = sheetState
        )
    }
}
