package it.facile.records.agent.domain.usecase

import it.facile.records.agent.domain.entity.local.BeerDetailUI
import it.facile.records.agent.domain.entity.local.FromBeerDetailToDetailUI
import it.facile.records.agent.domain.repository.Repository
import it.facile.records.agent.library.android.entity.Result
import javax.inject.Inject

class BeerDetailUseCase @Inject constructor(private val repository: Repository) : UseCase {

    suspend fun retrieveBeerDetailBy(id: Int): Result<List<BeerDetailUI>> {
        val detailsForBeer = repository.fetchBeerDetailBy(id)

        return when {
            detailsForBeer.succeded -> {
                return try {
                    val beerDetailForUI = mutableListOf<BeerDetailUI>()

                    (detailsForBeer as Result.Success).data.forEach { beerDetail ->
                        beerDetailForUI.add(FromBeerDetailToDetailUI().mapFrom(beerDetail!!))
                    }
                    Result.Success(beerDetailForUI)

                } catch (e: Exception) {
                    Result.Error(e)
                }
            }
            else -> {
                Result.Error((detailsForBeer as Result.Error).exception)
            }

        }
    }
}

