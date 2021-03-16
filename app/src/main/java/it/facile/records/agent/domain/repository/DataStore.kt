package it.facile.records.agent.domain.repository

import it.facile.records.agent.domain.entity.local.FileOfRecord

/**
 * Simple interface for Datastores. Local or Remote.
 *  *
 * */
interface DataStore {
    suspend fun getFilesForRecordsBy(id: Int): List<FileOfRecord?> {
        TODO("Not yet implemented")
    }
}
