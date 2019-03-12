package com.sk.metofficeweather.util

import android.content.Context
import com.sk.metofficeweather.base.MyApplicationClass

/*
 * Created by Sambhaji Karad on 09-03-2019
*/

object AppPreferenceStorage {

    private const val mAppPref = "mAppPref"

    private const val IS_DATA_DOWNLOAD = "is_data_download"

    fun saveIsDataDownload(status: Boolean) {
        val hxPrefs = MyApplicationClass.appContext!!.getSharedPreferences(mAppPref, Context.MODE_PRIVATE)
        val editor = hxPrefs.edit()
        editor.putBoolean(IS_DATA_DOWNLOAD, status)

        isDataDownload = status
        editor.apply()
    }

    var isDataDownload: Boolean? = false
        get() {
            val hxPrefs = MyApplicationClass.appContext!!.getSharedPreferences(mAppPref, Context.MODE_PRIVATE)
            isDataDownload = hxPrefs.getBoolean(IS_DATA_DOWNLOAD, false)
            return field
        }
        private set
}