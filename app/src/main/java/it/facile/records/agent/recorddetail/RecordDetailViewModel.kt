package it.facile.records.agent.recorddetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.facile.records.agent.di.RecordDetail
import it.facile.records.agent.domain.entity.local.FileOfRecordUI
import it.facile.records.agent.domain.usecase.RecordDetailUseCase
import it.facile.records.agent.domain.usecase.UseCase
import it.facile.records.agent.library.android.entity.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordDetailViewModel @Inject constructor(
    @RecordDetail private val usecase: UseCase,
) : ViewModel() {
    private val _filesForRecord = MutableLiveData<List<FileOfRecordUI?>>()
    val recordDetail: MutableLiveData<List<FileOfRecordUI?>>
        get() = _filesForRecord

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _showError = MutableLiveData<Boolean>()
    val showError: LiveData<Boolean>
        get() = _showError

    fun fetchRecordDetail(id: Int) {
        val useCase = usecase as RecordDetailUseCase

        viewModelScope.launch {
            when (val retrieveBeerDetailBy = useCase.retrievefilesForRecordBy(id)) {
                is Result.Success -> _filesForRecord.postValue(retrieveBeerDetailBy.data)
                is Result.Loading -> _isLoading.postValue(true)
                is Result.Error -> _showError.postValue(true)
            }
        }
    }
}