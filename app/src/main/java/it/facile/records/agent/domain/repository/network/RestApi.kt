package it.facile.records.agent.domain.repository.network

import it.facile.records.agent.domain.entity.remote.RecordListDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface RestApi {
    @Headers("Content-type:application/json")
    @GET("records")
    suspend fun retrieveRecordsFromRemote(): Response<RecordListDTO>
}
