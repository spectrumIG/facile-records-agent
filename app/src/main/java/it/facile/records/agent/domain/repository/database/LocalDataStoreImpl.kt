package it.facile.records.agent.domain.repository.database

import it.facile.records.agent.domain.entity.local.RecordDetail
import it.facile.records.agent.domain.repository.DataStore
import it.facile.records.agent.library.android.entity.Result
import javax.inject.Inject

/**
 //TODO Add Room as a dependency
 * */
class LocalDataStoreImpl @Inject constructor(private val database: LocalDatabase) : DataStore{
    override suspend fun gerRecordDetailBy(id: Int): Result<List<RecordDetail?>> {
        return super.gerRecordDetailBy(id)
    }
}
