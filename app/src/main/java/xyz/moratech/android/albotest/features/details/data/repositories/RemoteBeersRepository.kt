package xyz.moratech.android.albotest.features.details.data.repositories

import xyz.moratech.android.albotest.features.details.data.repositories.api.BeersApi
import xyz.moratech.android.albotest.features.details.domain.models.Beer
import xyz.moratech.android.albotest.features.details.domain.repositories.BeersRepository

class RemoteBeersRepository(
    private val beersApi: BeersApi
) : BeersRepository {

    companion object {
        private const val ELEMENTS_TO_LOAD = 10
    }

    override suspend fun fetchBeers(pageToLoad: Int): List<Beer> {
        return try {
            if (pageToLoad != 0) {
                beersApi.fetchBeersFromPage(
                    pageNumber = pageToLoad,
                    elementsToLoad = ELEMENTS_TO_LOAD
                )
            } else {
                beersApi.fetchBeers()
            }
        } catch (exception: Exception) {
            throw exception
        }
    }

    override suspend fun saveBeers(params: List<Beer>) {
        print(params.size)
    }
}