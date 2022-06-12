package com.example.trakbucks.data

import androidx.lifecycle.LiveData

class TransactionRepository(private val transactionDao: TransactionDao) {

    val allTransactions : LiveData<List<Transaction>> = transactionDao.readAlldata()

    suspend fun addTransaction( transaction: Transaction){
        transactionDao.addTransaction(transaction)
    }

    suspend fun updateTransaction (transaction: Transaction){
        transactionDao.updateTransaction(transaction)
    }
}