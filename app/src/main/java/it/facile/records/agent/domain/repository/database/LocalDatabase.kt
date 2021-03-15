package it.facile.records.agent.domain.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import it.facile.records.agent.domain.entity.local.Record
import it.facile.records.agent.domain.repository.database.dao.RecordsDao

/**
 * The Room database for this app
 */
const val DATABASE_NAME = "app-db"

@Database(entities = [Record::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordsDao
//    abstract fun plantDao(): PlantDao

    companion object {

        // Use a singleton instaciation as suggested by Google.
        @Volatile
        private var instance: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): LocalDatabase {
            return Room.databaseBuilder(context, LocalDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}