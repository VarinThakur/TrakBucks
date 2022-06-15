package com.example.trakbucks.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

import androidx.versionedparcelable.VersionedParcelize
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "All_Transactions_Table")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val personImage: String,
    val name: String,
    val amount: String,
    val date: String,
    val time : String,
    val type: Int
    ):Parcelable

