package xyz.moratech.android.albotest.features.details.view.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import retrofit2.HttpException
import xyz.moratech.android.albotest.features.details.domain.models.Beer
import xyz.moratech.android.albotest.features.details.domain.usecases.FetchBeersUseCase
import xyz.moratech.android.albotest.features.details.domain.usecases.SaveDataUseCase

class DetailsViewModel(
    private val fetchBeersUseCase: FetchBeersUseCase,
    private val saveDataUseCase: SaveDataUseCase
) : ViewModel() {

    val beersLiveData = MutableLiveData<List<Beer>>()
    val errorLiveData = MutableLiveData<String>()
    val loadingLiveData = MutableLiveData<Boolean>()

    private var currentPage = 1

    fun fetchBeers() {
        try {
            fetchBeersUseCase.invoke(viewModelScope, currentPage) {
                currentPage++
                it.second?.let { beers ->
                    saveDataUseCase.invoke(viewModelScope, beers) {}
                    beersLiveData.postValue(beers)
                } ?: it.first?.let { failure ->
                    processFailure(failure)
                }
            }
        } catch (exception: Exception) {
            processFailure(exception)
        }
    }

    private fun processFailure(exception: Exception) {
        exception.printStackTrace()
        if (exception is HttpException) {
            errorLiveData.postValue(exception.code().toString())
        } else {
            errorLiveData.postValue("Unknown")
        }
    }

    fun loadMoreData(scrolledPosition: Int, currentListSize: Int) {
        if (scrolledPosition == currentListSize) {
            fetchBeers()
            loadingLiveData.postValue(true)
        } else {
            loadingLiveData.postValue(false)
        }
    }

    fun clearAndFetch() {
        currentPage = 1
        fetchBeers()
    }
}