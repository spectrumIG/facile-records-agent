package it.facile.records.agent.domain.repository

import it.facile.records.agent.di.LocalDataStore
import it.facile.records.agent.di.RemoteDataStore
import it.facile.records.agent.domain.entity.local.FromDtoToBusinessMapper
import it.facile.records.agent.domain.entity.local.RecordBusinessData
import it.facile.records.agent.domain.entity.local.RecordDetail
import it.facile.records.agent.domain.repository.network.RemoteStore
import it.facile.records.agent.library.android.entity.Result
import javax.inject.Inject
import it.facile.records.agent.domain.repository.database.LocalDataStoreImpl as LocalData

interface Repository {

    suspend fun getAllRecords(): Result<List<RecordBusinessData?>>

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

    override suspend fun getAllRecords(): Result<List<RecordBusinessData?>> {
        return when (val allrecords = remoteDataStore.getAllrecords()) {
            is Result.Success -> {
                val recordsForBusiness = mutableListOf<RecordBusinessData>()
                allrecords.data.forEach { recordDto ->
                    recordsForBusiness.add(FromDtoToBusinessMapper().mapFrom(recordDto))
                }
                Result.Success(recordsForBusiness)
            }

            else -> allrecords as Result.Error
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
