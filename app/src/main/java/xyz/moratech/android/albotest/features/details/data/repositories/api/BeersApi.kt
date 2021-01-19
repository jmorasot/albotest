package xyz.moratech.android.albotest.features.details.data.repositories.api

import retrofit2.http.GET
import retrofit2.http.Query
import xyz.moratech.android.albotest.features.details.domain.models.Beer

interface BeersApi {
    @GET("beers")
    suspend fun fetchBeers(): List<Beer>

    @GET("beers")
    suspend fun fetchBeersFromPage(
        @Query("page") pageNumber: Int,
        @Query("per_page") elementsToLoad: Int
    ): List<Beer>
}