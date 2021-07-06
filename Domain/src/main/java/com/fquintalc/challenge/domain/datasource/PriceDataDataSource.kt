package com.fquintalc.challenge.domain.datasource

import com.fquintalc.mychallenge.models.PriceData
import java.util.*

interface PriceDataDataSource {

    fun getInfo(date : Date?) : List<PriceData>
}