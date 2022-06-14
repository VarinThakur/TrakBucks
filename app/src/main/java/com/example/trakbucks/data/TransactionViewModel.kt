package com.example.trakbucks.data

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.example.trakbucks.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Time
import java.util.*

class TransactionViewModel(private val transactionDao: TransactionDao) :ViewModel() {

    val allTransactions : LiveData<List<Transaction>>
    private val repository: TransactionRepository

    init {
        //val transactionDao= TransactionDatabase.getDatabase(application).transactionDao()
        repository = TransactionRepository(transactionDao)
        allTransactions= repository.allTransactions
        Log.d("Model","View Model created!")
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

class TransactionViewModelFactory(private val transactionDao: TransactionDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransactionViewModel(transactionDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

