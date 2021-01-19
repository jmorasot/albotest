package xyz.moratech.android.albotest.features.details.domain.usecases

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred

interface UseCase<T, R> {
    var backgroundJob: Deferred<Pair<Exception?, R?>>?

    fun invoke(scope: CoroutineScope, params: T, listener: (Pair<Exception?, R?>) -> Unit)
}
