package com.fquintalc.mychallenge.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Singleton
    fun getDefaultRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://run.mocky.io/").addConverterFactory(GsonConverterFactory.create()).build()
    }

}