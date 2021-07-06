package com.fquintalc.mychallenge.domain.datasource.remote.service

import com.fquintalc.mychallenge.models.PriceData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PriceDataService {

    @GET("v3/cc4c350b-1f11-42a0-a1aa-f8593eafeb1e/")
    fun downloadData(@Query("date") date : String) : Call<List<PriceData>>
}