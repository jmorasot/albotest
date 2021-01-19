package xyz.moratech.android.albotest.features.details.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import xyz.moratech.android.albotest.features.details.data.repositories.LocalBeersRepository
import xyz.moratech.android.albotest.features.details.data.repositories.RemoteBeersRepository
import xyz.moratech.android.albotest.features.details.data.repositories.api.BeersApi
import xyz.moratech.android.albotest.features.details.data.repositories.local.BeersDao
import xyz.moratech.android.albotest.features.details.data.repositories.local.BeersDatabase
import xyz.moratech.android.albotest.features.details.domain.repositories.BeersRepository
import xyz.moratech.android.albotest.features.details.domain.usecases.FetchBeersUseCase
import xyz.moratech.android.albotest.features.details.domain.usecases.SaveDataUseCase
import xyz.moratech.android.albotest.features.details.view.viewmodels.DetailsViewModel
import xyz.moratech.android.albotest.features.details.view.viewmodels.MainViewModel
import xyz.moratech.android.albotest.features.di.NetworkModule

object DetailsModule {
    private const val CONTENT_LOCAL = "CONTENT_LOCAL"
    private const val CONTENT_REMOTE = "CONTENT_REMOTE"

    val module by lazy {
        module {
            viewModel {
                DetailsViewModel(
                    fetchBeersUseCase = get(),
                    saveDataUseCase = get()
                )
            }

            viewModel {
                MainViewModel()
            }

            factory {
                FetchBeersUseCase(
                    beersRepository = get(named(CONTENT_REMOTE))
                )
            }

            factory {
                SaveDataUseCase(
                    beersRepository = get(named(CONTENT_LOCAL))
                )
            }

            factory<BeersRepository>(named(CONTENT_LOCAL))  {
                LocalBeersRepository(
                    beersDao = provideDao(get())
                )
            }

            factory<BeersRepository>(named(CONTENT_REMOTE))  {
                RemoteBeersRepository(
                    beersApi = provideApi(retrofit = get(named(NetworkModule.BEERS_API)))
                )
            }

            single { BeersDatabase.instance(androidContext()) }
        }
    }

    private fun provideDao(database: BeersDatabase) : BeersDao{
        return database.beersDao()
    }

    private fun provideApi(retrofit: Retrofit): BeersApi {
        return retrofit.create(BeersApi::class.java)
    }
}