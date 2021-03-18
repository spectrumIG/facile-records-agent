package it.facile.records.agent.recordslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.facile.records.agent.di.RecordsList
import it.facile.records.agent.domain.entity.local.RecordForUi
import it.facile.records.agent.domain.usecase.RecordsListUsecase
import it.facile.records.agent.domain.usecase.UseCase
import it.facile.records.agent.library.android.entity.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordsListViewModel @Inject constructor(
   @RecordsList private val usecase: UseCase
) : ViewModel() {
    private val _records = MutableLiveData<Result<List<RecordForUi>>>()
    val records: LiveData<Result<List<RecordForUi>>>
        get() = _records

    private val _showProgress = MutableLiveData(false)
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    init {
        getAllRecords()
    }

    fun getAllRecords() {
        val recordsListUsecase = usecase as RecordsListUsecase

        viewModelScope.launch {
            _showProgress.postValue(true)

            _records.postValue(recordsListUsecase.retrieveRecords())

            _showProgress.postValue(false)
        }
    }

}
