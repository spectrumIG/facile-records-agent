package it.facile.records.agent.domain.usecase

import it.facile.records.agent.domain.entity.local.RecordForUi
import it.facile.records.agent.domain.repository.Repository
import it.facile.records.agent.library.android.entity.Result
import javax.inject.Inject

class RecordsListUsecase @Inject constructor(
    private val repository: Repository)
    : UseCase {

    suspend fun retrieveRecords(): Result<List<RecordForUi>> {
        return when (val records = repository.getAllRecordsFromServer()) {
            is Result.Success -> {
                val returnedList = mutableListOf<RecordForUi>()

                records.data.forEach { recordBusinessData ->
//                    recordBusinessData?.mapTo(repository.checkIfRecordHasFile(recordId = recordBusinessData.id))?.let { returnedList.add(it) }
                    recordBusinessData?.mapTo()?.let { returnedList.add(it) }

                }
                Result.Success(returnedList)
            }
            else -> {
                Result.Error((records as Result.Error).exception)
            }
        }
    }

}
