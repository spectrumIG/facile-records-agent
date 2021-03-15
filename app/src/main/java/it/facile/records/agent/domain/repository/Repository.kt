package it.facile.records.agent.domain.repository

import it.facile.records.agent.di.RemoteDataStore
import it.facile.records.agent.domain.entity.local.BeerDetail
import it.facile.records.agent.domain.entity.local.SimpleBeer
import it.facile.records.agent.domain.repository.network.RemoteStore
import it.facile.records.agent.library.android.entity.Result
import javax.inject.Inject

interface Repository {

    suspend fun fetchAllBeerPaginated(page: Int): Result<List<SimpleBeer?>>

    suspend fun fetchPaginatedBeersForDate(page: Int, brewedBefore: String, brewedAfter: String): Result<List<SimpleBeer?>>

    suspend fun fetchBeerDetailBy(id:Int) : Result<List<BeerDetail?>>
}

/**
 * Main entry point for Single-source-of-truth pattern.
 * Should contain Local data store but for sake of simplicity and time
 * i'm gonna use just the remote Data Store.
 *
 * */

@ExperimentalStdlibApi
class RepositoryImpl @Inject constructor(
    @RemoteDataStore private val remoteDataStore: RemoteStore
) : Repository {

    override suspend fun fetchAllBeerPaginated(page: Int): Result<List<SimpleBeer?>> {
        return try {
            remoteDataStore.getAllBeersList(page = page, null, null)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun fetchPaginatedBeersForDate(page: Int, brewedBefore: String, brewedAfter: String): Result<List<SimpleBeer?>> {
        return try {
            remoteDataStore.getAllBeersList(page = page, brewedBefore = brewedBefore, brewedAfter = brewedAfter)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun fetchBeerDetailBy(id: Int): Result<List<BeerDetail?>> {
        return try {
            remoteDataStore.getBeerDetailBy(id)
        } catch (e: Exception) {
            Result.Error(e)
        }

    }
}
