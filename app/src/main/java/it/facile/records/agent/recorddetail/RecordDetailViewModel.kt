package it.facile.records.agent.recorddetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.facile.records.agent.di.InsertFile
import it.facile.records.agent.di.RecordDetail
import it.facile.records.agent.domain.entity.local.FileOfRecordUI
import it.facile.records.agent.domain.usecase.InsertFileUseCase
import it.facile.records.agent.domain.usecase.RecordDetailUseCase
import it.facile.records.agent.domain.usecase.UseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordDetailViewModel @Inject constructor(
    @RecordDetail private val getRecordsUsecase: UseCase,
    @InsertFile private val insertUsecase: UseCase,
) : ViewModel() {
//    private val _filesForRecord = MutableLiveData<List<FileOfRecordUI?>>()
    lateinit var  fileForRecord: LiveData<List<FileOfRecordUI?>>
//        get() = _filesForRecord

//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean>
//        get() = _isLoading
//
//    private val _showError = MutableLiveData<Boolean>()
//    val showError: LiveData<Boolean>
//        get() = _showError

    fun fetchRecordDetail(id: Int) {
        val useCase = getRecordsUsecase as RecordDetailUseCase

        viewModelScope.launch {
             fileForRecord =  useCase.retrievefilesForRecordBy(id).asLiveData()
        }
    }

    fun attachFileToRecord(recordId: Int, filename: String, filesize: Long) {
        val useCase = insertUsecase as InsertFileUseCase
        viewModelScope.launch {
            useCase.insertFileForRecord(
                recordId = recordId,
                filename = filename,
                filesize = filesize
            )
        }
    }
}