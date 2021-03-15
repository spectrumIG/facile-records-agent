package it.facile.records.agent.domain.repository.network

import it.facile.records.agent.di.RemoteDataStore
import it.facile.records.agent.domain.entity.local.SimpleBeer
import it.facile.records.agent.domain.repository.DataStore
import it.facile.records.agent.library.android.entity.Result
import javax.inject.Inject

@RemoteDataStore
class RemoteStore @Inject constructor(private val restApi: RestApi) : DataStore {

    @ExperimentalStdlibApi
    suspend fun getAllrecords(): Result<List<SimpleBeer>> {
        val response = restApi.retrieveRecordsFromRemote()
        return when {
            response.isSuccessful -> {
                val simpleBeeList = mutableListOf<SimpleBeer>()

                response.body()?.forEach { modelDTO ->
//                    simpleBeeList.add(FromDtoToSimpleBeerMapper().mapFrom(beerListDTO))
                }
                return Result.Success(simpleBeeList)
            }

            else -> Result.Error(Exception(response.message()))
        }
    }


}
