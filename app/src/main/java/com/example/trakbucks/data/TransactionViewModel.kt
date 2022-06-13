package com.example.trakbucks.data

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Time
import java.util.*

class TransactionViewModel(application: Application) :AndroidViewModel(application) {

    val allTransactions : LiveData<List<Transaction>>
    private val repository: TransactionRepository

    init {
        val transactionDao= TransactionDatabase.getDatabase(application).transactionDao()
        repository = TransactionRepository(transactionDao)
        allTransactions= repository.allTransactions
    }

    fun addTransaction(transaction: Transaction){
        viewModelScope.launch(Dispatchers.IO) { // to run in background thread
            repository.addTransaction(transaction)
        }
    }

    fun updateTransaction(transaction: Transaction){
        viewModelScope.launch(Dispatchers.IO) { // to run in background thread
            repository.updateTransaction(transaction)
        }
    }

    fun deleteTransaction(transaction: Transaction){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTransaction(transaction)
        }
    }

    fun deleteAllTransactions(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTransactions()
        }
    }


}

