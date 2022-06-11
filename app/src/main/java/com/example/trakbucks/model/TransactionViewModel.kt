package com.example.trakbucks.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.sql.Time
import java.util.*

class TransactionViewModel :ViewModel() {
    private var _name= MutableLiveData<String>("")
    val name: LiveData<String> get() = _name

    private var _amount= MutableLiveData<String>("")
    val amount: LiveData<String> get() = _amount

    private var _imageId= MutableLiveData<Int>()
    val imageId :LiveData<Int> get()= _imageId

    private var _date= MutableLiveData<Date>()
    val date: LiveData<Date> get()= _date

    private var _time= MutableLiveData<Time>()
    val time: LiveData<Time> = _time

    private var _theme= MutableLiveData<String>()
    val theme: LiveData<String> = _theme

    private var _currency= MutableLiveData<String>()
    val currency: LiveData<String> = _currency

    private var _profileName= MutableLiveData<String>("")
    val profileName: LiveData<String> get() = _profileName

    private var _profileImageId= MutableLiveData<Int>()
    val profileImageId :LiveData<Int> get()= _profileImageId

    private var _sumTransactions= MutableLiveData<Int>()
    val sumTransactions: LiveData<Int> get() = _sumTransactions

    private var _totalIncome= MutableLiveData<Int>()
    val totalIncome: LiveData<Int> get() = _totalIncome

    private var _totalExpenditure= MutableLiveData<Int>()
    val totalExpenditure: LiveData<Int> get() = _totalExpenditure


    fun setName(newName: String){
        _name.value= newName
    }

    fun setAmount(newAmount: String){
        _amount.value= newAmount
    }

    fun setImage(newImageId: Int){
        _imageId.value= newImageId
    }

    fun setDate(newDate: Date){
        _date.value= newDate
    }

    fun setTime(newTime: Time){
        _time.value= newTime
    }

    fun setProfileImage(newProfileImageId: Int){
        _profileImageId.value= newProfileImageId
    }

    fun setTheme(newTheme: String){
        _theme.value= newTheme
    }

    fun setCurrency(newCurrency: String){
        _currency.value= newCurrency
    }

    fun setSumTransactions ( newSumTransactions : Int){

        _sumTransactions.value = newSumTransactions
    }

    fun setTotalIncome ( newTotalIncome : Int){

        _totalIncome.value = newTotalIncome
    }

    fun setTotalExpenditure ( newTotalExpenditure : Int){
        _totalExpenditure.value= newTotalExpenditure
    }

    fun hasNoNameSet(): Boolean {
        return _name.value.isNullOrEmpty()
    }

    fun hasNoAmountSet(): Boolean {
        return _amount.value.isNullOrEmpty()
    }

    fun hasNoImageSet(): Boolean {
        return true
    }

    fun hasNoDateSet(): Boolean {
        return true
    }

    fun hasNoTimeSet(): Boolean {
        return true
    }

    fun hasNoThemeSet(): Boolean {
        return _theme.value.isNullOrEmpty()
    }

    fun hasNoCurrencySet(): Boolean {
        return _currency.value.isNullOrEmpty()
    }

    fun hasNoProfileImageSet(): Boolean {
        return true
    }

    fun hasNoProfileNameset() :Boolean {
        return _profileName.value.isNullOrEmpty()
    }

}

