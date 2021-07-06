package com.fquintalc.mychallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fquintalc.mychallenge.models.PriceData
import com.fquintalc.mychallenge.toDate
import com.fquintalc.mychallenge.usecase.pricedata.GetPriceDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class GraphViewModel @Inject constructor(private val getPriceDataUseCase: GetPriceDataUseCase) :
    ViewModel() {

    private val dataList = ArrayList<PriceData>()

    val data = MutableLiveData(dataList)

    fun updateData() {
        if (dataList.isEmpty())
            getData(null)
        else
            getData(dataList.last().date.toDate(DATE_FORMAT))
    }

    fun downloadData() {
        getData(null)
    }

    private fun getData(date: Date?) {
        viewModelScope.launch(Dispatchers.IO) {
            getPriceDataUseCase(date).let {
                if (it.isNotEmpty()) {
                    dataList.addAll(it)
                    viewModelScope.launch(Dispatchers.Main) {
                        data.value = dataList
                    }

                }
            }

        }
    }

    companion object
    {
        const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS"
    }
}