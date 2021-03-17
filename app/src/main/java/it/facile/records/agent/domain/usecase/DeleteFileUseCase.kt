package it.facile.records.agent.domain.usecase

import it.facile.records.agent.domain.repository.Repository
import javax.inject.Inject

class DeleteFileUseCase @Inject constructor(
    private val repository: Repository,
) : UseCase {

    suspend fun deleteFile(filename: String, recordId: Int) {
        return repository.deleteFile(filename, recordId)

    }
}