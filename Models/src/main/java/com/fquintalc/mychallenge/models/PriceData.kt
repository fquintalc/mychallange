package com.fquintalc.mychallenge.models

import java.util.*

class PriceData @JvmOverloads constructor(

    var date: String = "",
    var price: Double = 0.0,
    var percentageChange: Double = 0.0,
    var volume: Double = 0.0,
    var change: Double = 0.0
) {
}