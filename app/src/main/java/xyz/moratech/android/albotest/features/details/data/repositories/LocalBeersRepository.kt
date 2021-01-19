package xyz.moratech.android.albotest.features.details.data.repositories

import com.google.gson.Gson
import xyz.moratech.android.albotest.features.details.data.repositories.local.BeersDao
import xyz.moratech.android.albotest.features.details.data.repositories.local.BeersEntity
import xyz.moratech.android.albotest.features.details.domain.models.Beer
import xyz.moratech.android.albotest.features.details.domain.repositories.BeersRepository

class LocalBeersRepository(
    private val beersDao: BeersDao
) : BeersRepository {
    private val gsonParser = Gson()

    override suspend fun fetchBeers(pageToLoad: Int): List<Beer> {
        val beers = mutableListOf<Beer>()
        val list = beersDao.getBeers()
        list.forEach { beersEntity ->
            val beer = gsonParser.fromJson(beersEntity.attrs, Beer::class.java)
            beers.add(beer)
        }
        return beers
    }

    override suspend fun saveBeers(params: List<Beer>) {
        params.forEach {beer ->
            beersDao.saveBeer(BeersEntity(gsonParser.toJson(beer)))
        }
    }
}