package com.jaehl.spacex.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jaehl.spacex.data.local.configuration.Configuration
import com.jaehl.spacex.data.remote.spacexApi.SpacexApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class SpacexApiUrl

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    @SpacexApiUrl
    fun spacexApiUrl(config: Configuration): String = "${config.getSpacexApiUrl()}/"

    @Provides
    @Singleton
    fun gson(): Gson = GsonBuilder()
        .serializeNulls()
        .create()

    @Provides
    @Singleton
    fun gsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun okHttpClient(config: Configuration): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .readTimeout(config.getRequestTimeout().toLong(), TimeUnit.SECONDS)
            .connectTimeout(config.getRequestTimeout().toLong(), TimeUnit.SECONDS)
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun retrofit(
        @SpacexApiUrl baseUrl: String,
        factory: GsonConverterFactory,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(factory)
            .build()
    }

    @Provides
    @Singleton
    fun spacexApi(retrofit: Retrofit): SpacexApi = retrofit.create(SpacexApi::class.java)

}