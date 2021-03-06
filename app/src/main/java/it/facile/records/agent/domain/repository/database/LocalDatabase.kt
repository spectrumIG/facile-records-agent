package it.facile.records.agent.domain.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import it.facile.records.agent.domain.entity.local.Converters
import it.facile.records.agent.domain.entity.local.Record
import it.facile.records.agent.domain.entity.local.RecordFile
import it.facile.records.agent.domain.repository.database.dao.RecordsDao

/**
 * The Room database for this app
 */
const val DATABASE_NAME = "app-db"

@Database(entities = [Record::class,RecordFile::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordsDao

    companion object {

        // Use a singleton as suggested by Google.
        @Volatile
        private var instance: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): LocalDatabase {
            return Room.databaseBuilder(context, LocalDatabase::class.java, DATABASE_NAME).fallbackToDestructiveMigration()
                .build()
        }
    }
}