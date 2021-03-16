package it.facile.records.agent.domain.repository.database

import it.facile.records.agent.domain.entity.local.FileOfRecord
import it.facile.records.agent.domain.repository.DataStore
import it.facile.records.agent.domain.repository.database.dao.RecordsDao
import javax.inject.Inject


class LocalDataStoreImpl @Inject constructor(private val recordsDao: RecordsDao) : DataStore {

    override suspend fun getFilesForRecordsBy(id: Int): List<FileOfRecord?> {
        return recordsDao.getAllFileForRecordById(id).filesForRecords.map { recordFile ->
            FileOfRecord(filename = recordFile.filename, fileSize = recordFile.fileSize, addingDate = recordFile.addingDate)
        }
    }


}
