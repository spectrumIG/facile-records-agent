package it.facile.records.agent.domain.repository.database

import it.facile.records.agent.domain.entity.local.RecordDetail
import it.facile.records.agent.domain.repository.DataStore
import it.facile.records.agent.library.android.entity.Result
import javax.inject.Inject

/**
 * Placeholder in case of needs
 * */
class LocalDataStore @Inject constructor() : DataStore{
    override suspend fun gerRecordDetailBy(id: Int): Result<List<RecordDetail?>> {
        return super.gerRecordDetailBy(id)
    }
}
