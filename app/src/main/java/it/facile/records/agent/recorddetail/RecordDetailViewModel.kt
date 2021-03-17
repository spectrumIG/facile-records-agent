package it.facile.records.agent.recorddetail

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import it.facile.records.agent.di.InsertFile
import it.facile.records.agent.di.RecordDetail
import it.facile.records.agent.domain.entity.local.FileOfRecordUI
import it.facile.records.agent.domain.usecase.InsertFileUseCase
import it.facile.records.agent.domain.usecase.RecordDetailUseCase
import it.facile.records.agent.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordDetailViewModel @Inject constructor(
    private val defaulDispatcher: CoroutineDispatcher,
    @RecordDetail private val getRecordsUsecase: UseCase,
    @InsertFile private val insertUsecase: UseCase,
) : ViewModel() {

    private var job: Job? = null

    var fileForRecord: LiveData<List<FileOfRecordUI?>> = MutableLiveData()

    fun fetchRecordDetail(id: Int) {
        val useCase = getRecordsUsecase as RecordDetailUseCase

        fileForRecord = liveData {
            emitSource(useCase.retrievefilesForRecordBy(id).asLiveData())
        }
    }

    fun attachFileToRecord(recordId: Int, filename: String, filesize: Long) {
        val useCase = insertUsecase as InsertFileUseCase
        job = viewModelScope.launch() {
            useCase.insertFileForRecord(
                recordId = recordId,
                filename = filename,
                filesize = filesize
            )
        }
    }

    override fun onCleared() {
        job?.isActive ?: job?.cancel()
        super.onCleared()
    }
}