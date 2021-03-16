package it.facile.records.agent.domain.repository.database.dao

import androidx.room.*
import it.facile.records.agent.domain.entity.local.Record
import it.facile.records.agent.domain.entity.local.RecordFile
import it.facile.records.agent.domain.entity.local.RecordWithFiles

@Dao
interface RecordsDao {
    @Transaction
    @Query("SELECT * FROM Record")
    suspend fun getAllFileForRecords(): List<RecordWithFiles>

    @Transaction
    @Query("SELECT * FROM Record WHERE record_id = :recordId")
    suspend fun getAllFileForRecordById(recordId: Int): RecordWithFiles

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRecord(records: List<Record>)

    @Delete
    suspend fun deleteRecord(record: Record)

    @Query("SELECT EXISTS(SELECT 1 FROM record_table WHERE record_id= :id)")
    suspend fun recordWithIdHasFilesAttached(id: Int): Boolean

    @Insert
    suspend fun insertFileForRecord(recordFile: RecordFile)

}