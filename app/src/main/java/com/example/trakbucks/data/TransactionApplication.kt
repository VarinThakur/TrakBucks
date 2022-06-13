package com.example.trakbucks.data

import android.app.Application

class TransactionApplication : Application() {
    val database: TransactionDatabase by lazy { TransactionDatabase.getDatabase(this) }
}