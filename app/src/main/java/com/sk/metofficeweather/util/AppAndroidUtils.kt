package com.sk.metofficeweather.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import com.sk.metofficeweather.R
import com.sk.metofficeweather.base.MyApplicationClass
import java.text.DateFormatSymbols

/*
 * Created by Sambhaji Karad on 08-03-2019
*/

object AppAndroidUtils {

    fun startFwdAnimation(activity: Activity) {
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun isNetWorkAvailable(showMessage: Boolean): Boolean {
        val connMgr = MyApplicationClass.appContext!!
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = connMgr.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            return true
        } else if (showMessage) {
            Toast.makeText(
                MyApplicationClass.appContext,
                MyApplicationClass.appContext!!.getString(R.string.hint_network_error),
                Toast.LENGTH_LONG
            ).show()
        }
        return false
    }

    fun isNetWorkAvailable(): Boolean {
        return isNetWorkAvailable(true)
    }

    fun getMonthForInt(num: Int): String {
        var month = ""
        val dateFormatSymbols = DateFormatSymbols()
        val months = dateFormatSymbols.months
        if (num in 1..12) {
            month = months[num-1]
        }
        return month
    }
}
