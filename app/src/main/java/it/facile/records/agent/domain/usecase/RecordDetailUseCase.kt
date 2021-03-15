package it.facile.records.agent.domain.usecase

import it.facile.records.agent.domain.entity.local.RecordDetailUI
import it.facile.records.agent.domain.repository.Repository
import it.facile.records.agent.library.android.entity.Result
import javax.inject.Inject

class RecordDetailUseCase @Inject constructor(private val repository: Repository) : UseCase {

    suspend fun retrieveBeerDetailBy(id: Int): Result<List<RecordDetailUI>> {
        val detailsForBeer = repository.fetchRecordDetailBy(id)

        return when {
            detailsForBeer.succeded -> {
                return try {
                    val beerDetailForUI = mutableListOf<RecordDetailUI>()

                    (detailsForBeer as Result.Success).data.forEach { beerDetail ->
//                        beerDetailForUI.add(FromBeerDetailToDetailUI().mapFrom(beerDetail!!))
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

