package it.facile.records.agent.domain.repository.database

import it.facile.records.agent.domain.entity.local.FileOfRecord
import it.facile.records.agent.domain.entity.local.RecordFile
import it.facile.records.agent.domain.repository.DataStore
import it.facile.records.agent.domain.repository.database.dao.RecordsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class LocalDataStoreImpl @Inject constructor(private val recordsDao: RecordsDao) : DataStore {

    suspend fun getFilesForRecordsBy(id: Int): Flow<List<FileOfRecord?>> {
        return recordsDao.getAllFileForRecordById(id).map { value ->
            value.map { recordFile ->
                FileOfRecord(recordFile.filename, recordFile.fileSize, recordFile.addingDate)
            }
        }
    }

    suspend fun insertFileForRecord(file: RecordFile) {
        recordsDao.insertFileForRecord(file)
    }


}
