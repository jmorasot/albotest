package xyz.moratech.android.albotest.features.details.domain.usecases

import kotlinx.coroutines.*
import xyz.moratech.android.albotest.features.details.domain.models.Beer
import xyz.moratech.android.albotest.features.details.domain.repositories.BeersRepository

class SaveDataUseCase(
    private val beersRepository: BeersRepository
): UseCase<List<Beer>, Boolean> {
    override var backgroundJob: Deferred<Pair<Exception?, Boolean?>>? = null

    override fun invoke(
        scope: CoroutineScope,
        params: List<Beer>,
        listener: (Pair<Exception?, Boolean?>) -> Unit
    ) {
        backgroundJob = scope.async(Dispatchers.IO) {
            try {
                beersRepository.saveBeers(params)
                Pair(null, true)
            } catch (exception: Exception) {
                Pair(exception, null)
            }
        }

        scope.launch { listener(backgroundJob!!.await()) }
    }

}
