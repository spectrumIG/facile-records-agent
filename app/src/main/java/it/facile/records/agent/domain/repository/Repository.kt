package it.facile.records.agent.domain.repository

import it.facile.records.agent.di.LocalDataStore
import it.facile.records.agent.di.RemoteDataStore
import it.facile.records.agent.domain.entity.local.FileOfRecordBusiness
import it.facile.records.agent.domain.entity.local.RecordBusinessData
import it.facile.records.agent.domain.repository.network.RemoteStore
import it.facile.records.agent.library.android.entity.Result
import javax.inject.Inject
import it.facile.records.agent.domain.repository.database.LocalDataStoreImpl as LocalData

interface Repository {

    suspend fun getAllRecordsFromServer(): Result<List<RecordBusinessData?>>

    suspend fun fetchRecordFileListByRecord(id: Int): Result<List<FileOfRecordBusiness?>>

    suspend fun checkIfRecordHasFile(recordId:Int) : Boolean
}

/**
 * Main entry point for Single-source-of-truth pattern.
 * */

@ExperimentalStdlibApi
class RepositoryImpl @Inject constructor(
    @LocalDataStore private val localDataStore: LocalData,
    @RemoteDataStore private val remoteDataStore: RemoteStore
) : Repository {

    override suspend fun getAllRecordsFromServer(): Result<List<RecordBusinessData?>> {
        return when (val allrecords = remoteDataStore.getAllrecords()) {
            is Result.Success -> {
                val recordsForBusiness = mutableListOf<RecordBusinessData>()
                allrecords.data.forEach { recordDto ->
                    recordsForBusiness.add(
                        recordDto.maptoRecord().mapToBusiness()
                    )
                }
                Result.Success(recordsForBusiness)
            }

            else -> allrecords as Result.Error
        }
    }

    // TODO: 16/03/21 This needs to be implemented checking the DB
    override suspend fun checkIfRecordHasFile(recordId: Int): Boolean {
        return false
    }

    override suspend fun fetchRecordFileListByRecord(id: Int): Result<List<FileOfRecordBusiness?>> {
        return try {
            Result.Success(localDataStore.getFilesForRecordsBy(id).map { fileForRecord ->
                fileForRecord?.mapToBusiness()
            })
        } catch (e: Exception) {
            Result.Error(e)
        }

    }
}
