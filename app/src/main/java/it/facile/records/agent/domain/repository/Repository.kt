package it.facile.records.agent.domain.repository

import it.facile.records.agent.di.LocalDataStore
import it.facile.records.agent.di.RemoteDataStore
import it.facile.records.agent.domain.entity.local.RecordDetail
import it.facile.records.agent.domain.entity.local.SimpleBeer
import it.facile.records.agent.domain.repository.network.RemoteStore
import it.facile.records.agent.library.android.entity.Result
import javax.inject.Inject
import it.facile.records.agent.domain.repository.database.LocalDataStore as LocalData

interface Repository {

    suspend fun getAllRecords(page: Int): Result<List<SimpleBeer?>>

    suspend fun fetchRecordDetailBy(id: Int): Result<List<RecordDetail?>>
}

/**
 * Main entry point for Single-source-of-truth pattern.
 * Should contain Local data store but for sake of simplicity and time
 * i'm gonna use just the remote Data Store.
 *
 * */

@ExperimentalStdlibApi
class RepositoryImpl @Inject constructor(
    @LocalDataStore private val localDataStore: LocalData,
    @RemoteDataStore private val remoteDataStore: RemoteStore
) : Repository {

    override suspend fun getAllRecords(page: Int): Result<List<SimpleBeer?>> {
        return try {
            remoteDataStore.getAllrecords()
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun fetchRecordDetailBy(id: Int): Result<List<RecordDetail?>> {
        return try {
            localDataStore.gerRecordDetailBy(id)
        } catch (e: Exception) {
            Result.Error(e)
        }

    }
}
