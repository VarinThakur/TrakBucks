package com.example.trakbucks.data

import android.app.Application
import com.example.trakbucks.data.TransactionDatabase

class TransactionApplication : Application() {
    val database: TransactionDatabase by lazy { TransactionDatabase.getDatabase(this) }
}