package xyz.moratech.android.albotest.features.details.domain.usecases

import kotlinx.coroutines.*
import xyz.moratech.android.albotest.features.details.domain.models.Beer
import xyz.moratech.android.albotest.features.details.domain.repositories.BeersRepository

class FetchBeersUseCase(
    private val beersRepository: BeersRepository
) : UseCase<Int?, List<Beer>?> {

    override var backgroundJob: Deferred<Pair<Exception?, List<Beer>?>>? = null

    override fun invoke(
        scope: CoroutineScope,
        params: Int?,
        listener: (Pair<Exception?, List<Beer>?>) -> Unit
    ) {
        backgroundJob = scope.async(Dispatchers.IO) {
            try {
                val result = beersRepository.fetchBeers(params ?: 0)
                Pair(null, result)
            } catch (exception: Exception) {
                Pair(exception, null)
            }
        }

        scope.launch { listener(backgroundJob!!.await()) }
    }
}