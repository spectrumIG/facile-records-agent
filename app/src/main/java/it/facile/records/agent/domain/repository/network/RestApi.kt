package it.facile.records.agent.domain.repository.network

import it.facile.records.agent.domain.entity.remote.BeerDetailDTO
import it.facile.records.agent.domain.entity.remote.BeersListDTOItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface RestApi {

    @GET("beers")
    suspend fun getAllBeersWithPagination(
        @Query("page") page: Int?,
        @QueryMap timeFilters: Map<String,String>? = null
    ):
        Response<List<BeersListDTOItem>>

    @GET("beers/{beerId}")
    suspend fun getDetailOfABeer(
        @Path("beerId") beerId: Int
    ): Response<List<BeerDetailDTO>>
}
