package it.facile.records.agent.domain.usecase

import it.facile.records.agent.domain.entity.local.BeerForUi
import it.facile.records.agent.domain.entity.local.FromSimpleToUiBeerMapper
import it.facile.records.agent.domain.repository.Repository
import it.facile.records.agent.library.android.entity.Result
import javax.inject.Inject

class BeersListUsecase @Inject constructor(private val repository: Repository) : UseCase {

    suspend fun retrieveBeersPaginated(page: Int?): Result<List<BeerForUi>> {
        val simpleBeers = repository.fetchAllBeerPaginated(page ?: 0)
        return when {
            simpleBeers.succeded -> {
                return try {
                    val returnedList = mutableListOf<BeerForUi>()

                    (simpleBeers as Result.Success).data.forEach {
                        returnedList.add(FromSimpleToUiBeerMapper().mapFrom(it!!))
                    }

                    Result.Success(returnedList)
                } catch (e: Exception) {
                    Result.Error(e)
                }
            }
            else -> {
                Result.Error((simpleBeers as Result.Error).exception)
            }
        }
    }

    suspend fun retrieveBeersPaginatedFor(page: Int?, brewedBefore: String?, brewedAfter: String?): Result<List<BeerForUi>> {
        val simpleBeers = repository.fetchPaginatedBeersForDate(page = page ?: 0, brewedBefore = brewedBefore ?: "", brewedAfter = brewedAfter ?: "")

        return when {
            simpleBeers.succeded -> {
                val returnedList = mutableListOf<BeerForUi>()

                (simpleBeers as Result.Success).data.forEach {
                    returnedList.add(FromSimpleToUiBeerMapper().mapFrom(it!!))
                }

                return Result.Success(returnedList)
            }
            else -> {
                Result.Error((simpleBeers as Result.Error).exception)
            }
        }
    }
}
