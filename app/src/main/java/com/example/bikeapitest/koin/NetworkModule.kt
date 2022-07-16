package com.example.bikeapitest.koin

import android.app.Application
import android.util.Log
import com.example.bikeapitest.BuildConfig.DEBUG
import com.example.bikeapitest.R
import com.example.bikeapitest.extensions.NetworkManager
import com.example.bikeapitest.extensions.tagName
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    fun provideNetworkManager() = NetworkManager()

    val HTTP_LOG_TAG = "http".tagName

    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .setLenient()
            .setPrettyPrinting()
        return gsonBuilder.create()
    }

    fun provideHttpInterceptorLogger(): HttpLoggingInterceptor.Logger {
        return HttpLoggingInterceptor.Logger { message -> Log.d(HTTP_LOG_TAG, message) }
    }

    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpLoggingInterceptor(logger: HttpLoggingInterceptor.Logger): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(logger)
        if (DEBUG) {
            interceptor.apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
        return interceptor
    }

    fun provideHttpClient(cache: Cache, loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(40, TimeUnit.SECONDS)
            .connectTimeout(40, TimeUnit.SECONDS)
            .callTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .cache(cache)

        okHttpClientBuilder.build()
        return okHttpClientBuilder.build()
    }

    fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
    }

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get(), get()) }
    single { provideNetworkManager() }
    single { provideHttpInterceptorLogger() }
    single { provideHttpLoggingInterceptor(get()) }
    single { provideGson() }
    single {
        val baseUrl = androidContext().getString(R.string.BASE_URL)
        provideRetrofit(get(), baseUrl)
    }
}
