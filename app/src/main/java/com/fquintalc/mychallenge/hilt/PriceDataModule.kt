package com.fquintalc.mychallenge.hilt

import com.fquintalc.challenge.domain.datasource.PriceDataDataSource
import com.fquintalc.challenge.domain.impl.DefaultPriceDataRepository
import com.fquintalc.challenge.domain.repository.PriceDataRepository
import com.fquintalc.mychallenge.domain.datasource.remote.RemotePriceDataDataSource
import com.fquintalc.mychallenge.usecase.pricedata.GetPriceDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PriceDataModule {

    private fun getRemoteDataSource(retrofit: Retrofit): PriceDataDataSource {
        return RemotePriceDataDataSource(retrofit)
    }

    @Provides
    @Singleton
    fun getPriceDataRepository(retrofit: Retrofit): PriceDataRepository {
        return DefaultPriceDataRepository(getRemoteDataSource(retrofit))
    }

    @Provides
    @Singleton
    fun getGetPriceDataUseCase(priceDataRepository: PriceDataRepository): GetPriceDataUseCase {
        return GetPriceDataUseCase(priceDataRepository)
    }
}