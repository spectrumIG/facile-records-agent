package it.facile.records.agent.recordfilelist

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import it.facile.records.agent.di.DeleteFile
import it.facile.records.agent.di.InsertFile
import it.facile.records.agent.di.RecordsFileList
import it.facile.records.agent.domain.entity.local.FileOfRecordUI
import it.facile.records.agent.domain.usecase.DeleteFileUseCase
import it.facile.records.agent.domain.usecase.InsertFileUseCase
import it.facile.records.agent.domain.usecase.RecordFileListUseCase
import it.facile.records.agent.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordDetailViewModel @Inject constructor(
    private val defaulDispatcher: CoroutineDispatcher,
    @RecordsFileList private val getRecordsFileUsecase: UseCase,
    @InsertFile private val insertUsecase: UseCase,
    @DeleteFile private val deleteFileUseCase: UseCase
    ) : ViewModel() {

    private var job: Job? = null

    var fileForRecord: LiveData<List<FileOfRecordUI?>> = MutableLiveData()

    fun fetchRecordDetail(id: Int) {
        val useCase = getRecordsFileUsecase as RecordFileListUseCase

        fileForRecord = liveData(defaulDispatcher) {
            emitSource(useCase.retrievefilesForRecordBy(id).asLiveData())
        }
    }

    fun attachFileToRecord(recordId: Int, filename: String, filesize: Long) {
        val useCase = insertUsecase as InsertFileUseCase
        job = viewModelScope.launch(defaulDispatcher) {
            useCase.insertFileForRecord(
                recordId = recordId,
                filename = filename,
                filesize = filesize
            )
        }
    }

    fun deleteAttachedFileById(recordName: String, fileId: Int) {
        val useCase = deleteFileUseCase as DeleteFileUseCase
        viewModelScope.launch(defaulDispatcher) {
            useCase.deleteFile(recordName, fileId)
        }
    }

    override fun onCleared() {
        job?.isActive ?: job?.cancel()
        super.onCleared()
    }
}