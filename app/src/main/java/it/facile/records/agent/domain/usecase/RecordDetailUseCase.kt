package it.facile.records.agent.domain.usecase

import it.facile.records.agent.domain.entity.local.FileOfRecordUI
import it.facile.records.agent.domain.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecordDetailUseCase @Inject constructor(private val dispatcher: CoroutineDispatcher, private val repository: Repository) : UseCase {

    suspend fun retrievefilesForRecordBy(id: Int): Flow<List<FileOfRecordUI?>> {
        return withContext(dispatcher) {
            repository.fetchRecordFileListByRecord(id).map { value ->
                value.map { fileOfRecordBusiness ->
                    fileOfRecordBusiness?.let {
                        FileOfRecordUI(
                            fileSize = it.fileSize,
                            addingDate = it.addingDate,
                            filename = it.filename)
                    }
                }
            }
        }
    }
}

