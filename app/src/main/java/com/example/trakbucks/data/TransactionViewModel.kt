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

class TransactionViewModel(application: Application) :AndroidViewModel(application) {

    val allTransactions : LiveData<List<Transaction>>
    private val repository: TransactionRepository

    private var _theme= MutableLiveData<String>()
    val theme: LiveData<String> = _theme

    private var _currency= MutableLiveData<String>()
    val currency: LiveData<String> = _currency

    private var _profileName= MutableLiveData<String>("")
    val profileName: LiveData<String> get() = _profileName

    private var _profileImageUri= MutableLiveData<Uri>()
    val profileImageId :LiveData<Uri> get()= _profileImageUri

    private var _totalIncome= MutableLiveData<Int>()
    val totalIncome: LiveData<Int> get() = _totalIncome

    private var _totalExpenditure= MutableLiveData<Int>()
    val totalExpenditure: LiveData<Int> get() = _totalExpenditure

    private var _sumTransactions= MutableLiveData<Int>()
    val sumTransactions: LiveData<Int> get() = _sumTransactions



    init {
        val transactionDao= TransactionDatabase.getDatabase(application).transactionDao()
        repository = TransactionRepository(transactionDao)
        allTransactions= repository.allTransactions
        Log.d("Model","View Model created!")
        reset()
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

    fun reset(){
        _theme.value = "Red"
        _currency.value = "INR"
        _totalIncome.value = 0
        _totalExpenditure.value = 0
        _sumTransactions.value = 0
        _profileName.value = "Name"
        _profileImageUri.value = Uri.parse("android.resource://com.example.trakbucks/" + R.drawable._abstract_user_icon_1)
        // dataBase Reset
    }

    fun updateTheme(theme : String)
    {
        _theme.value = theme
    }

    fun updateCurrency(currency: String)
    {
        _currency.value = currency
    }

    fun updateProfileImage(imageUri : Uri)
    {
        _profileImageUri.value = imageUri
    }

    fun updateProfileName(name : String)
    {
        _profileName.value = name
    }

}

