package com.example.trakbucks.data

import androidx.lifecycle.LiveData

class TransactionRepository(private val transactionDao: TransactionDao) {

    val allTransactions : LiveData<List<Transaction>> = transactionDao.readAlldata()
    val recentTransactions: LiveData<List<Transaction>> = transactionDao.getRecentTransactions()
    val userDetails: LiveData<List<User>> = transactionDao.getUserDetails()

    suspend fun addTransaction( transaction: Transaction){
        transactionDao.addTransaction(transaction)
    }

    suspend fun addUser( user: User){
        transactionDao.addUser(user)
    }

    suspend fun updateTransaction (transaction: Transaction){
        transactionDao.updateTransaction(transaction)
    }

    suspend fun updateUser(user: User){
        transactionDao.updateUser(user)
    }

    suspend fun deleteTransaction(transaction: Transaction){
        transactionDao.deleteTransaction(transaction)
    }

    suspend fun deleteAllTransactions(){
        transactionDao.deleteAllTransactions()
    }

    suspend fun deleteUserDetails(){
        transactionDao.deleteUserDetails()
    }
}