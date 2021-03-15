package it.facile.records.agent.recordslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.facile.records.agent.di.RecordsList
import it.facile.records.agent.domain.entity.local.BeerForUi
import it.facile.records.agent.domain.usecase.RecordsListUsecase
import it.facile.records.agent.domain.usecase.UseCase
import it.facile.records.agent.library.android.entity.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordsListViewModel @Inject constructor(
   @RecordsList private val usecase: UseCase
) : ViewModel() {
    private val _beers = MutableLiveData<Result<List<BeerForUi>>>()
    val beers: LiveData<Result<List<BeerForUi>>>
        get() = _beers

    private val _showProgress = MutableLiveData(false)
    val showProgress: LiveData<Boolean>
        get() = _showProgress



    fun fetchBeersPaginatedWith(page: Int) {
        val beersListUsecase = usecase as RecordsListUsecase

        viewModelScope.launch {
            _showProgress.postValue(true)

            _beers.postValue(beersListUsecase.retrieveBeersPaginated(page))

            _showProgress.postValue(false)
        }
    }

}
