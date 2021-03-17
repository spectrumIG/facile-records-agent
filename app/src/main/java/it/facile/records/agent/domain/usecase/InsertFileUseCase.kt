package it.facile.records.agent.domain.usecase

import it.facile.records.agent.domain.entity.local.RecordFile
import it.facile.records.agent.domain.repository.Repository
import java.util.*
import javax.inject.Inject

class InsertFileUseCase @Inject constructor(private val repository: Repository) : UseCase {

    suspend fun insertFileForRecord(recordId: Int, filename: String, filesize: Long){
        repository.insertFileForRecord(RecordFile(recordId = recordId,filename = filename,fileSize = filesize, addingDate = Calendar.getInstance()))
    }
}