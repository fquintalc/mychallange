package com.fquintalc.mychallenge.ui.fragment

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.fquintalc.mychallenge.R
import com.fquintalc.mychallenge.databinding.FragmentGraphBinding
import com.fquintalc.mychallenge.models.PriceData
import com.fquintalc.mychallenge.toDate
import com.fquintalc.mychallenge.toString
import com.fquintalc.mychallenge.viewmodel.GraphViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class GraphFragment : Fragment(R.layout.fragment_graph) {

    private val viewModel: GraphViewModel by activityViewModels()
    private var increment = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DataBindingUtil.bind<FragmentGraphBinding>(view)
        viewModel.data.observe(viewLifecycleOwner, {
            if (it != null && it.isNotEmpty())
                loadData(it, binding!!.chart)
        })
        viewModel.downloadData()
        val description = Description()
        description.text = getString(R.string.description_change_prices)
        binding!!.chart.description = description
    }

    private fun loadData(list: List<PriceData>, chart: LineChart) {
        chart.setTouchEnabled(true)
        val values = ArrayList<Entry>()
        list.forEach {
            values.add(
                Entry(
                    it.date.toDate(GraphViewModel.DATE_FORMAT).time.toFloat(),
                    it.price.toFloat()
                )
            )
        }

        var set1: LineDataSet

        if (chart.data != null &&
            chart.data.dataSetCount > 0
        ) {


            // set data
            lifecycleScope.launch(Dispatchers.Main) {
                set1 = chart.data.getDataSetByIndex(0) as LineDataSet
                set1.values = values
                set1.notifyDataSetChanged()
                chart.data.notifyDataChanged()
                chart.notifyDataSetChanged()
                chart.invalidate()
            }
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "Precios")
            set1.setDrawIcons(false)

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f)

            // black lines and points
            set1.color = Color.BLACK
            set1.setCircleColor(Color.BLACK)

            // line thickness and point size
            set1.lineWidth = 1f
            set1.circleRadius = 3f

            // draw points as solid circles
            set1.setDrawCircleHole(false)

            // customize legend entry
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f

            // text size of values
            set1.valueTextSize = 9f

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f)

            // set the filled area
            set1.setDrawFilled(true)
            set1.fillFormatter =
                IFillFormatter { dataSet, dataProvider -> chart.axisLeft.axisMinimum }

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.fade_red)
                set1.fillDrawable = drawable
            } else {
                set1.fillColor = Color.BLACK
            }
            val dataSets: ArrayList<ILineDataSet> = ArrayList()
            dataSets.add(set1) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)

            val xAxis = chart.xAxis
            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    val calendar = Calendar.getInstance()
                    calendar.time = Date(value.toLong())
                    val hour = calendar.get(Calendar.HOUR_OF_DAY)
                    val minute = calendar.get(Calendar.MINUTE)
                    return String.format("%02d:%02d", hour, minute)
                }
            }

            // set data
            lifecycleScope.launch(Dispatchers.Main) {
                chart.data = data
                chart.data.notifyDataChanged()
                chart.notifyDataSetChanged()
                chart.invalidate()
            }

        }


        lifecycleScope.launch(Dispatchers.IO) {
            val arrayList = java.util.ArrayList(list)
            delay(2000L)
            for(i in 1..10){
                val last = list.last()
                val date = last.date.toDate(GraphViewModel.DATE_FORMAT)
                val calendar = Calendar.getInstance()
                calendar.time = date
                calendar.add(Calendar.MINUTE,2)
                var price = last.price
                if(increment)
                    price+=15
                else
                    price-=15
                val newItem = PriceData(calendar.time.toString(GraphViewModel.DATE_FORMAT),price,0.0,0.0,0.0)

                arrayList.add(newItem)
                if(price>40000)
                {
                    increment = false
                }

                if(price<39000)
                {
                    increment = true
                }

            }

            loadData(arrayList,chart)
        }
    }
}