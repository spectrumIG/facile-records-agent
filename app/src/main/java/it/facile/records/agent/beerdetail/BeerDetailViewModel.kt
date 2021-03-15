package it.facile.records.agent.beerdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.facile.records.agent.di.BeerDetail
import it.facile.records.agent.domain.entity.local.BeerDetailUI
import it.facile.records.agent.domain.usecase.BeerDetailUseCase
import it.facile.records.agent.domain.usecase.UseCase
import it.facile.records.agent.library.android.entity.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerDetailViewModel @Inject constructor(
    @BeerDetail private val usecase: UseCase,
) : ViewModel() {
    private val _beerDetail = MutableLiveData<BeerDetailUI>()
    val beerDetail: LiveData<BeerDetailUI>
        get() = _beerDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _showError = MutableLiveData<Boolean>()
    val showError: LiveData<Boolean>
        get() = _showError

    fun fetchBeerDetail(id: Int) {
        val beerDetailUseCase = usecase as BeerDetailUseCase

        viewModelScope.launch {
            when (val retrieveBeerDetailBy = beerDetailUseCase.retrieveBeerDetailBy(id)) {
                is Result.Success -> _beerDetail.postValue(retrieveBeerDetailBy.data[0])
                is Result.Loading -> _isLoading.postValue(true)
                is Result.Error -> _showError.postValue(true)
            }
        }
    }
}