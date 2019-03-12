package com.sk.metofficeweather.util

/*
 * Created by Sambhaji Karad on 08-03-2019
*/

interface AppConstants {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "metoffice_weather.db"

        const val TABLE_TMAX = "temp_max"
        const val TABLE_TMIN = "temp_min"
        const val TABLE_RAIN_FALL = "rain_fall"

        const val SUCCESS = "success"
        const val FAIL = "fail"

        /*weather_info table column*/
        const val KEY_ID = "id"
        const val KEY_PLACE_NAME = "place_name"
        const val KEY_VALUE = "value"
        const val KEY_RAINFALL = "rainfall"
        const val KEY_TMAX = "tmax"
        const val KEY_TMIN = "tmin"
        const val KEY_YEAR = "year"
        const val KEY_MONTH = "month"
        const val NA = "N/A"
    }
}
