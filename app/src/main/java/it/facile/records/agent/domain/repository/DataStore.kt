package it.facile.records.agent.domain.repository

import it.facile.records.agent.domain.entity.local.RecordDetail
import it.facile.records.agent.library.android.entity.Result

/**
 * Simple interface for Datastores. Local or Remote.
 *  *
 * */
interface DataStore {
    suspend fun gerRecordDetailBy(id: Int): Result<List<RecordDetail?>> {
        TODO("Not yet implemented")
    }
}
