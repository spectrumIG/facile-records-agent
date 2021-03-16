package it.facile.records.agent.domain.usecase

import it.facile.records.agent.domain.entity.local.FileOfRecordUI
import it.facile.records.agent.domain.repository.Repository
import it.facile.records.agent.library.android.entity.Result
import javax.inject.Inject

class RecordDetailUseCase @Inject constructor(private val repository: Repository) : UseCase {

    suspend fun retrievefilesForRecordBy(id: Int): Result<List<FileOfRecordUI?>> {

        return when (val result = repository.fetchRecordFileListByRecord(id)) {
            is Result.Success -> {
                Result.Success(result.data.map { fileOfRecordBusiness ->
                    fileOfRecordBusiness?.mapToUI()
                })
            }
            else -> {
                Result.Error((result as Result.Error).exception)
            }

        }
    }
}

