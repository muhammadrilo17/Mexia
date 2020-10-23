package com.gemastik.android.mexia.ui.dashboard.progress

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gemastik.android.mexia.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.fragment_progress.*
import java.util.*
import kotlin.collections.ArrayList

class ProgressFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_progress, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progress_horizontal.max = 100

        val currentProgress = 60

        ObjectAnimator.ofInt(progress_horizontal, "progress", currentProgress)
            .setDuration(1000)
            .start()

        val yValues = ArrayList<Entry>()
        yValues.add(Entry(0F,40f))
        yValues.add(Entry(1F,30f))
        yValues.add(Entry(2F,20f))
        yValues.add(Entry(3F,40f))
        yValues.add(Entry(4F,20f))
        yValues.add(Entry(5F,60f))
        yValues.add(Entry(6F,40f))

        val set = LineDataSet(yValues, "Progress")
        val xAxis = ArrayList<String>()
        xAxis.addAll(getDay())

        set.fillAlpha = 110
        val dataSet = ArrayList<ILineDataSet>()
        dataSet.add(set)
        val data = LineData(dataSet)
        progressPH(xAxis, data)
        progressPK(xAxis, data)
        progressPuzzle(xAxis, data)
        progressTest(xAxis, data)
    }

    private fun progressPH(xAxis: ArrayList<String>, data: LineData){
        progress_pengenalan_huruf.isDragEnabled = true
        progress_pengenalan_huruf.setScaleEnabled(false)
        progress_pengenalan_huruf.xAxis.valueFormatter = IndexAxisValueFormatter(xAxis)
        progress_pengenalan_huruf.description.text = ""
        progress_pengenalan_huruf.extraRightOffset = 20f
        progress_pengenalan_huruf.axisRight.isEnabled = false
        progress_pengenalan_huruf.data = data
    }
    private fun progressPK(xAxis: ArrayList<String>, data: LineData){
        progress_pengenalan_kata.isDragEnabled = true
        progress_pengenalan_kata.setScaleEnabled(false)
        progress_pengenalan_kata.xAxis.valueFormatter = IndexAxisValueFormatter(xAxis)
        progress_pengenalan_kata.description.text = ""
        progress_pengenalan_kata.extraRightOffset = 20f
        progress_pengenalan_kata.axisRight.isEnabled = false
        progress_pengenalan_kata.data = data
    }
    private fun progressPuzzle(xAxis: ArrayList<String>, data: LineData){
        progress_puzzle.isDragEnabled = true
        progress_puzzle.setScaleEnabled(false)
        progress_puzzle.xAxis.valueFormatter = IndexAxisValueFormatter(xAxis)
        progress_puzzle.description.text = ""
        progress_puzzle.extraRightOffset = 20f
        progress_puzzle.axisRight.isEnabled = false
        progress_puzzle.data = data
    }
    private fun progressTest(xAxis: ArrayList<String>, data: LineData){
        progress_test.isDragEnabled = true
        progress_test.setScaleEnabled(false)
        progress_test.xAxis.valueFormatter = IndexAxisValueFormatter(xAxis)
        progress_test.description.text = ""
        progress_test.extraRightOffset = 20f
        progress_test.axisRight.isEnabled = false
        progress_test.data = data
    }

    private fun getDay(): ArrayList<String>{
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        val temp = ArrayList<String>()
        when(day){
            Calendar.SUNDAY -> {
                temp.add("Senin")
                temp.add("Selasa")
                temp.add("Rabu")
                temp.add("Kamis")
                temp.add("Jumat")
                temp.add("Sabtu")
                temp.add("Minggu")
            }
            Calendar.MONDAY -> {
                temp.add("Selasa")
                temp.add("Rabu")
                temp.add("Kamis")
                temp.add("Jumat")
                temp.add("Sabtu")
                temp.add("Minggu")
                temp.add("Senin")
            }
            Calendar.TUESDAY -> {
                temp.add("Rabu")
                temp.add("Kamis")
                temp.add("Jumat")
                temp.add("Sabtu")
                temp.add("Minggu")
                temp.add("Senin")
                temp.add("Selasa")
            }
            Calendar.WEDNESDAY -> {
                temp.add("Kamis")
                temp.add("Jumat")
                temp.add("Sabtu")
                temp.add("Minggu")
                temp.add("Senin")
                temp.add("Selasa")
                temp.add("Rabu")
            }
            Calendar.THURSDAY -> {
                temp.add("Jumat")
                temp.add("Sabtu")
                temp.add("Minggu")
                temp.add("Senin")
                temp.add("Selasa")
                temp.add("Rabu")
                temp.add("Kamis")
            }
            Calendar.FRIDAY -> {
                temp.add("Sabtu")
                temp.add("Minggu")
                temp.add("Senin")
                temp.add("Selasa")
                temp.add("Rabu")
                temp.add("Kamis")
                temp.add("Jumat")
            }
            Calendar.SATURDAY -> {
                temp.add("Minggu")
                temp.add("Senin")
                temp.add("Selasa")
                temp.add("Rabu")
                temp.add("Kamis")
                temp.add("Jumat")
                temp.add("Sabtu")
            }
        }
        return temp
    }
}