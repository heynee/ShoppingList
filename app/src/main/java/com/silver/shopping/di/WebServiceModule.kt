package com.silver.shopping.di

import com.google.gson.GsonBuilder
import com.silver.shopping.BuildConfig
import com.silver.shopping.api.WebService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class WebServiceModule {
    @Provides
    @Singleton
    fun provideWebService(): WebService {
        val gson = GsonBuilder().create()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(WebService::class.java)
    }
}