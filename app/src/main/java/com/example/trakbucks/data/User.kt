package com.example.trakbucks.data

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User_Details")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val profileImage : String,
    val name: String,
    val income: Int,
    val expenditure: Int,
    val total: Int
)

