package xyz.moratech.android.albotest

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import xyz.moratech.android.albotest.features.details.di.DetailsModule
import xyz.moratech.android.albotest.features.di.NetworkModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeInjector(this)
    }

    private fun initializeInjector(app: App) {
        startKoin {
            printLogger()
            androidContext(app)
        }

        loadKoinModules(
            listOf(
                NetworkModule.module,
                DetailsModule.module
            )
        )
    }
}