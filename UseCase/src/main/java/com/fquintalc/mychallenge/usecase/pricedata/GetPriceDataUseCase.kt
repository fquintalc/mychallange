package com.fquintalc.mychallenge.usecase.pricedata

import com.fquintalc.challenge.domain.repository.PriceDataRepository
import com.fquintalc.mychallenge.models.PriceData
import java.util.*

class GetPriceDataUseCase(private val priceDataRepository: PriceDataRepository) {

    suspend operator fun invoke(date: Date?) : List<PriceData>{
        return priceDataRepository.getInfo(date)
    }
}