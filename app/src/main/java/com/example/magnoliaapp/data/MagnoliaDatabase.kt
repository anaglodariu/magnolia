package com.example.magnoliaapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [VisitedMagnolia::class],
    version = 1,
    exportSchema = false
)
abstract class MagnoliaDatabase :
    RoomDatabase() {

    abstract fun visitedMagnoliaDao():
            VisitedMagnoliaDao

    companion object {

        @Volatile
        private var Instance:
                MagnoliaDatabase? = null

        fun getDatabase(
            context: Context
        ): MagnoliaDatabase {

            return Instance
                ?: synchronized(this) {

                    Room.databaseBuilder(
                        context,
                        MagnoliaDatabase::class.java,
                        "magnolia_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                        .also {
                            Instance = it
                        }
                }
        }
    }
}