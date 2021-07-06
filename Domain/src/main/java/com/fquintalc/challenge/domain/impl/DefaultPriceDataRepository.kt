package com.fquintalc.challenge.domain.impl

import com.fquintalc.challenge.domain.datasource.PriceDataDataSource
import com.fquintalc.challenge.domain.repository.PriceDataRepository
import com.fquintalc.mychallenge.models.PriceData
import java.util.*

class DefaultPriceDataRepository (private val remoteDataDataSource : PriceDataDataSource) : PriceDataRepository {
    override fun getInfo(date: Date?): List<PriceData> = remoteDataDataSource.getInfo(date)
}