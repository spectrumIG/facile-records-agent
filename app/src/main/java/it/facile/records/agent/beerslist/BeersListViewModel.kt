package it.facile.records.agent.beerslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.facile.records.agent.di.BeersList
import it.facile.records.agent.domain.entity.local.BeerForUi
import it.facile.records.agent.domain.usecase.BeersListUsecase
import it.facile.records.agent.domain.usecase.UseCase
import it.facile.records.agent.library.android.entity.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeersListViewModel @Inject constructor(
   @BeersList private val usecase: UseCase
) : ViewModel() {

    val filterTo = MutableLiveData("")

    val filterFrom = MutableLiveData("")

    internal val _isfiltered = MutableLiveData(false)
    val filtered: LiveData<Boolean>
        get() = _isfiltered

    private val _beers = MutableLiveData<Result<List<BeerForUi>>>()
    val beers: LiveData<Result<List<BeerForUi>>>
        get() = _beers

    private val _showProgress = MutableLiveData(false)
    val showProgress: LiveData<Boolean>
        get() = _showProgress



    fun fetchBeersPaginatedWith(page: Int) {
        val beersListUsecase = usecase as BeersListUsecase

        viewModelScope.launch {
            _showProgress.postValue(true)

            _beers.postValue(beersListUsecase.retrieveBeersPaginated(page))

            _showProgress.postValue(false)
        }
    }

    fun fetchBeersPaginatedAndWithFilterDate(page: Int, brewedBefore: String?, brewedAfter: String?) {
        val beersListUsecase = usecase as BeersListUsecase
        viewModelScope.launch {
            _showProgress.postValue(true)
            when (val retrieveBeersFor = beersListUsecase.retrieveBeersPaginatedFor(page, brewedBefore, brewedAfter)) {
                is Result.Success -> {
                    if(retrieveBeersFor.data.isNotEmpty()) {
                        _beers.postValue(retrieveBeersFor)
                        _showProgress.postValue(false)
                    }
                }
                else -> {
                    _beers.postValue(retrieveBeersFor)
                }
            }
            _showProgress.postValue(false)
        }
    }
}
