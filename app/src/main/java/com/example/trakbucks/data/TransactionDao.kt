package com.example.trakbucks.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTransaction(transaction: Transaction)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Query("Delete from All_transactions_table")
    suspend fun deleteAllTransactions()

    @Query("Delete from User_Details")
    suspend fun deleteUserDetails()

    @Query("Select * from All_transactions_table")
    fun readAlldata(): LiveData<List<Transaction>>

    @Query("Select * from User_Details")
    fun getUserDetails(): LiveData<List<User>>



}