package it.facile.records.agent.domain.repository.network

import it.facile.records.agent.di.RemoteDataStore
import it.facile.records.agent.domain.entity.local.BeerDetail
import it.facile.records.agent.domain.entity.local.FromDTOToBeerDetailMapper
import it.facile.records.agent.domain.entity.local.FromDtoToSimpleBeerMapper
import it.facile.records.agent.domain.entity.local.SimpleBeer
import it.facile.records.agent.domain.repository.DataStore
import it.facile.records.agent.library.android.entity.Result
import javax.inject.Inject

@RemoteDataStore
class RemoteStore @Inject constructor(private val restApi: RestApi) : DataStore {

    @ExperimentalStdlibApi
    suspend fun getAllBeersList(page: Int?, brewedBefore: String?, brewedAfter: String?): Result<List<SimpleBeer>> {
        val response = restApi.getAllBeersWithPagination(page, fillQueryMapForFilter(brewedAfter, brewedBefore))
        return when {
            response.isSuccessful -> {
                val simpleBeeList = mutableListOf<SimpleBeer>()

                response.body()?.forEach { beerListDTO ->
                    simpleBeeList.add(FromDtoToSimpleBeerMapper().mapFrom(beerListDTO))
                }
                return Result.Success(simpleBeeList)
            }

            else -> Result.Error(Exception(response.message()))
        }
    }

    suspend fun getBeerDetailBy(id: Int): Result<List<BeerDetail>> {
        val response = restApi.getDetailOfABeer(id)
        return when {
            response.isSuccessful -> {
                val beerDetail = mutableListOf<BeerDetail>()
                response.body()?.forEach { beerDetailDTO ->
                    beerDetail.add(FromDTOToBeerDetailMapper().mapFrom(beerDetailDTO))
                }
                return Result.Success(beerDetail)
            }

            else -> Result.Error(Exception(response.message()))
        }
    }

    @ExperimentalStdlibApi
    private fun fillQueryMapForFilter(brewedAfter: String?, brewedBefore: String?): Map<String, String> {
        return buildMap {
            if(!brewedAfter.isNullOrEmpty()) {
                this["brewed_after"] = brewedAfter
            }
            if(!brewedBefore.isNullOrEmpty()) {
                this["brewed_before"] = brewedBefore
            }
        }
    }
}
