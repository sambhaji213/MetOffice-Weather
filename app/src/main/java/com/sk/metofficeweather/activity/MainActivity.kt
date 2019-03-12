package com.sk.metofficeweather.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.sk.metofficeweather.R
import com.sk.metofficeweather.adapter.MonthWeatherAdapter
import com.sk.metofficeweather.database.MyAppDbHelper
import com.sk.metofficeweather.model.Weather
import com.sk.metofficeweather.util.Locations
import kotlinx.android.synthetic.main.activity_main.*

/*
 * Created by Sambhaji Karad on 08-3-2019
*/

class MainActivity : AppCompatActivity() {

    private var myAppDbHelper: MyAppDbHelper? = null
    private var yearsList: ArrayList<String> = ArrayList()
    private var placeName: String? = null
    private var years: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setBackgroundDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.bg_gradient))

        myAppDbHelper = MyAppDbHelper(this@MainActivity)
        yearsList = myAppDbHelper!!.getYearsList()

        setUpSpinnerData()
        setUpViewListener()
    }

    private fun setUpViewListener() {
        spinnerLocations.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                placeName = spinnerLocations.selectedItem.toString()
                if (!placeName.isNullOrEmpty() && !years.isNullOrEmpty()) {
                    getSortedWeatherData()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        spinnerYears.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                years = spinnerYears.selectedItem.toString()
                if (!placeName.isNullOrEmpty() && !years.isNullOrEmpty()) {
                    getSortedWeatherData()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun getSortedWeatherData() {
        val weatherList = myAppDbHelper!!.getYearWeatherDataList(placeName!!, years!!)
        if (weatherList.isNotEmpty()) {
            setUpDataToRecyclerView(weatherList)
        }
    }

    private fun setUpDataToRecyclerView(weatherList: ArrayList<Weather>) {
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = MonthWeatherAdapter(this, weatherList)
    }

    private fun setUpSpinnerData() {
        val arrayAdapterYears = ArrayAdapter(this, R.layout.spinner_item, yearsList)
        arrayAdapterYears.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerYears.adapter = arrayAdapterYears

        val arrayAdapterLocations = ArrayAdapter(this, R.layout.spinner_item, Locations.values())
        arrayAdapterLocations.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLocations.adapter = arrayAdapterLocations
    }
}
