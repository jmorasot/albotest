package xyz.moratech.android.albotest.features.di

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {
    const val BEERS_API = "BEERS_API"

    val module by lazy {
        module {
            single(named(BEERS_API)) {
                provideRetrofit()
            }
        }
    }

    private fun provideRetrofit(): Retrofit {

        val builder = Retrofit.Builder()
            .baseUrl("https://api.punkapi.com/v2/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        builder.client(client())
        return builder.build()
    }

    private fun client(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        var clientBuilder = OkHttpClient.Builder()
                .addInterceptor(interceptor)
        clientBuilder = clientBuilder.apply {
            connectTimeout(60, TimeUnit.SECONDS)
            callTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            readTimeout(100, TimeUnit.SECONDS)
        }

        return clientBuilder.build()
    }
}