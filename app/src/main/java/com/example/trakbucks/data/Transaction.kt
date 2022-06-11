package com.example.trakbucks.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "All_Transactions_Table")
data class Transaction(
    @PrimaryKey val id: Int,
    val personImage: Int,
    val name: String,
    val amount: String,
    val date: String,
    val time : String,
    val type: Int )

