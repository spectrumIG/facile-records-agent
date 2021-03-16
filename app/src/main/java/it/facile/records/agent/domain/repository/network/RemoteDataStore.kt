package it.facile.records.agent.domain.repository.network

import it.facile.records.agent.di.RemoteDataStore
import it.facile.records.agent.domain.entity.remote.RecordListDTO.RecordDTO
import it.facile.records.agent.domain.repository.DataStore
import it.facile.records.agent.library.android.entity.Result
import javax.inject.Inject

@RemoteDataStore
class RemoteStore @Inject constructor(private val restApi: RestApi) : DataStore {

    @ExperimentalStdlibApi
    suspend fun getAllrecords(): Result<List<RecordDTO>> {
        return try {
            val data = restApi.retrieveRecordsFromRemote().body()?.records?.toList()
            Result.Success(data.orEmpty())
        } catch (e: Exception) {
            Result.Error(Exception(e.message))
        }
    }


}
