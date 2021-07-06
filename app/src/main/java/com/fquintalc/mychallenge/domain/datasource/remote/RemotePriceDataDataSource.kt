package com.fquintalc.mychallenge.domain.datasource.remote

import com.fquintalc.challenge.domain.datasource.PriceDataDataSource
import com.fquintalc.mychallenge.domain.datasource.remote.service.PriceDataService
import com.fquintalc.mychallenge.models.PriceData
import retrofit2.Retrofit
import java.util.*

class RemotePriceDataDataSource(private val retrofit: Retrofit) : PriceDataDataSource {
    override fun getInfo(date: Date?): List<PriceData> {
        val response = retrofit.create(PriceDataService::class.java).downloadData("").execute()
        return response.body() ?: ArrayList<PriceData>()
    }
}