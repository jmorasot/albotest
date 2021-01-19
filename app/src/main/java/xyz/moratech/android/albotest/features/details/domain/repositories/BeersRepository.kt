package xyz.moratech.android.albotest.features.details.domain.repositories

import xyz.moratech.android.albotest.features.details.domain.models.Beer
import kotlin.jvm.Throws

interface BeersRepository {

    @Throws(Exception::class)
    suspend fun fetchBeers(pageToLoad: Int): List<Beer>

    suspend fun saveBeers(params: List<Beer>)
}
