package com.example.trakbucks.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Transaction::class], version = 1, exportSchema = false)
abstract class TransactionDatabase :RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

    companion object{  //used to create singleton instance of db, multiple instances of db are expensive
        @Volatile //writes to this field are immediately visible to other threads
        private var INSTANCE: TransactionDatabase? = null

        fun getDatabase(context: Context) : TransactionDatabase{
            val tempInstance = INSTANCE
            if(tempInstance!=null)  //if the database instance already exists, then return the instance
                return tempInstance

            synchronized(this){   // otherwise create the instance in the synchronized block pass context,
                // db, and name of db, synchronized block protects everything inside it from concurrent execution from multiple threads.
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    TransactionDatabase::class.java,
                    "All_transactions_database",
                ).build()
                INSTANCE= instance
                return instance
            }
        }
    }
}