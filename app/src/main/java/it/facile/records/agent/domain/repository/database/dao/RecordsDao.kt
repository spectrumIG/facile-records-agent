package it.facile.records.agent.domain.repository.database.dao

import androidx.room.*
import it.facile.records.agent.domain.entity.local.Record
import it.facile.records.agent.domain.entity.local.RecordFile
import it.facile.records.agent.domain.entity.local.RecordWithFiles
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordsDao {
    @Transaction
    @Query("SELECT * FROM Record")
    suspend fun getAllFileForRecords(): List<RecordWithFiles>

    @Query("SELECT DISTINCT * FROM file_table WHERE record_id = :recordId ORDER BY file_name")
    fun getAllFileForRecordById(recordId: Int): Flow<List<RecordFile>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRecord(records: List<Record>)

    @Delete
    suspend fun deleteRecord(record: Record)

    @Query("SELECT EXISTS(SELECT 1 FROM file_table WHERE record_id= :id)")
    suspend fun recordWithIdHasFilesAttached(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFileForRecord(recordFile: RecordFile)

    @Delete
    suspend fun deleteFileForRecord(recordFile: RecordFile)

}