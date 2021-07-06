package com.fquintalc.challenge.domain.repository

import com.fquintalc.mychallenge.models.PriceData
import java.util.*

interface PriceDataRepository {

    fun getInfo(date : Date?) : List<PriceData>

}