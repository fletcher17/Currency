package com.example.mycurrencyapp.di

import com.example.mycurrencyapp.api.CurrencyApi
import com.example.mycurrencyapp.common.ApiConstants
import com.example.mycurrencyapp.common.ApiConstants.API_KEY
import com.example.mycurrencyapp.domain.CurrencyRepository
import com.example.mycurrencyapp.repository.CurrencyRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSymbolsApi(interceptor: Interceptor): CurrencyApi {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(interceptor)
                .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
            request.addHeader("apikey", API_KEY)
            chain.proceed(request.build())
        }
    }


    @Provides
    @Singleton
    fun provideCurrencyRepository(api: CurrencyApi): CurrencyRepository {
        return CurrencyRepositoryImpl(api)
    }
}