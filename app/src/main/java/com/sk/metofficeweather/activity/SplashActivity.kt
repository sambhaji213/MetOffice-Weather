package com.sk.metofficeweather.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.sk.metofficeweather.R
import com.sk.metofficeweather.database.MyAppDbHelper
import com.sk.metofficeweather.model.Weather
import com.sk.metofficeweather.network.ApiClient
import com.sk.metofficeweather.network.ApiInterface
import com.sk.metofficeweather.util.*
import kotlinx.android.synthetic.main.activity_splash.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
 * Created by Sambhaji Karad on 08-3-2019
*/

class SplashActivity : AppCompatActivity() {

    private val TAG = SplashActivity::class.java.simpleName
    private var myAppDbHelper: MyAppDbHelper? = null

    private var resultCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.setBackgroundDrawable(ContextCompat.getDrawable(this@SplashActivity, R.drawable.bg_gradient))

        myAppDbHelper = MyAppDbHelper(this@SplashActivity)

        Handler().postDelayed({
            if (!AppPreferenceStorage.isDataDownload!!) {
                llLoader.visibility = View.VISIBLE
                getWeatherData()
            } else {
                llLoader.visibility = View.GONE
                openMainActivity()
            }
        }, 500)
    }

    private fun getWeatherData() {
        //Iteration on location and metrics
        if (AppAndroidUtils.isNetWorkAvailable()) {
            myAppDbHelper!!.clearData()
            val locationsList = Locations.values()
            val metricsList = Metrics.values()
            for (placeName in locationsList) {
                for (metricName in metricsList) {
                    getDataFromAPI(metricName, placeName)
                }
            }
        } else {
            finish()
        }
    }

    private fun getDataFromAPI(metricName: Metrics, placeName: Locations) {
        val apiService = ApiClient.getClient()!!.create(ApiInterface::class.java)

        val call = apiService.getMetOfficeData(metricName.name, placeName.name)

        call.enqueue(object : Callback<ArrayList<Weather>> {
            override fun onResponse(call: Call<ArrayList<Weather>>, response: Response<ArrayList<Weather>>) {
                Log.d(TAG, AppConstants.SUCCESS)
                resultCount++
                when (metricName.name) {
                    Metrics.Rainfall.name -> myAppDbHelper!!.insertRainfallList(response.body()!!, placeName.name)
                    Metrics.Tmax.name -> myAppDbHelper!!.insertTmaxList(response.body()!!, placeName.name)
                    Metrics.Tmin.name -> myAppDbHelper!!.insertTminList(response.body()!!, placeName.name)
                }
                verifyAPICall()
            }

            override fun onFailure(call: Call<ArrayList<Weather>>, t: Throwable) {
                Log.d(TAG, AppConstants.FAIL)
            }
        })
    }

    private fun verifyAPICall() {
        if (resultCount == 12) {
            AppPreferenceStorage.saveIsDataDownload(true)
            openMainActivity()
        }
    }

    private fun openMainActivity() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
        AppAndroidUtils.startFwdAnimation(this@SplashActivity)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //Do nothing
    }
}
