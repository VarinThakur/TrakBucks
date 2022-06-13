package com.example.trakbucks.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTransaction(transaction: Transaction)

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Query("Delete from All_transactions_table")
    suspend fun deleteAllTransactions()

    @Query("Select * from All_transactions_table")
    fun readAlldata(): LiveData<List<Transaction>>



}