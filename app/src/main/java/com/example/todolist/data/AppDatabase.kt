package com.example.todolist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolist.domain.Todo

// Database Class Definitions
@Database(entities = [Todo::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    // Create DAO Object
    abstract fun todoDAO(): TodoDAO

    // Singleton instance
    companion object {
        private var instance: AppDatabase? = null

        // make instance
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null)
                synchronized(AppDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "Todo.db"
                    )
                        .build()
                }
            return instance
        }
    }

}