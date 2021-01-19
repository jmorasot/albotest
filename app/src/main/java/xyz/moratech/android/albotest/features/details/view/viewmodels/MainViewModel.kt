package xyz.moratech.android.albotest.features.details.view.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import xyz.moratech.android.albotest.features.details.domain.models.Beer

class MainViewModel : ViewModel() {

    private var beer: Beer? = null
    val navigationLiveData = MutableLiveData<Boolean>()

    fun saveSelectedBeer(beer: Beer?) {
        this.beer = beer
        navigationLiveData.value = beer == null
    }

    fun requestBeer(): Beer? {
        return beer
    }
}