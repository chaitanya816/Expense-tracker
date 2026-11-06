package com.example.data

import kotlinx.coroutines.flow.Flow

class ExpenseRepository(private val transactionDao: TransactionDao) {

    val allTransactions: Flow<List<TransactionEntity>> = transactionDao.getAllTransactions()

    suspend fun insertTransaction(transaction: TransactionEntity): Long {
        return transactionDao.insert(transaction)
    }

    suspend fun deleteTransaction(transaction: TransactionEntity) {
        transactionDao.delete(transaction)
    }

    suspend fun deleteById(id: Long) {
        transactionDao.deleteById(id)
    }

    suspend fun clearAll() {
        transactionDao.deleteAll()
    }

    suspend fun populateInitialDataIfEmpty() {
        if (transactionDao.getCount() == 0) {
            transactionDao.insertAll(SampleData.generateInitialTransactions())
        }
    }

    suspend fun resetWithSampleData() {
        transactionDao.deleteAll()
        transactionDao.insertAll(SampleData.generateInitialTransactions())
    }
}
