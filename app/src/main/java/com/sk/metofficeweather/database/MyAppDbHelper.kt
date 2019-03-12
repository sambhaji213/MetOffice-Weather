package com.sk.metofficeweather.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.sk.metofficeweather.model.Weather
import com.sk.metofficeweather.util.AppConstants


/**
 * Created by Sambhaji Karad on 09-03-2019.
 */

class MyAppDbHelper(context: Context) :
    SQLiteOpenHelper(context, AppConstants.DATABASE_NAME, null, AppConstants.DATABASE_VERSION) {

    init {
        val db = this.readableDatabase
        db.close()
    }

    override fun onCreate(db: SQLiteDatabase) {
        val tableTmax = ("CREATE TABLE " + AppConstants.TABLE_TMAX + "("
                + AppConstants.KEY_ID + " INTEGER PRIMARY KEY," +
                AppConstants.KEY_PLACE_NAME + " TEXT, " +
                AppConstants.KEY_VALUE + " TEXT, " +
                AppConstants.KEY_YEAR + " TEXT, " +
                AppConstants.KEY_MONTH + " TEXT" + ")")

        val tableTmin = ("CREATE TABLE " + AppConstants.TABLE_TMIN + "("
                + AppConstants.KEY_ID + " INTEGER PRIMARY KEY," +
                AppConstants.KEY_PLACE_NAME + " TEXT, " +
                AppConstants.KEY_VALUE + " TEXT, " +
                AppConstants.KEY_YEAR + " TEXT, " +
                AppConstants.KEY_MONTH + " TEXT" + ")")

        val tableRainFall = ("CREATE TABLE " + AppConstants.TABLE_RAIN_FALL + "("
                + AppConstants.KEY_ID + " INTEGER PRIMARY KEY," +
                AppConstants.KEY_PLACE_NAME + " TEXT, " +
                AppConstants.KEY_VALUE + " TEXT, " +
                AppConstants.KEY_YEAR + " TEXT, " +
                AppConstants.KEY_MONTH + " TEXT" + ")")
        db.execSQL(tableTmax)
        db.execSQL(tableTmin)
        db.execSQL(tableRainFall)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val deleteTmax = "DELETE FROM " + AppConstants.TABLE_TMAX
        val deleteTmin = "DELETE FROM " + AppConstants.TABLE_TMIN
        val deleteRainfall = "DELETE FROM " + AppConstants.TABLE_RAIN_FALL
        db.execSQL(deleteTmax)
        db.execSQL(deleteTmin)
        db.execSQL(deleteRainfall)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun clearData(){
        val db = this.writableDatabase
        val deleteTmax = "DELETE FROM " + AppConstants.TABLE_TMAX
        val deleteTmin = "DELETE FROM " + AppConstants.TABLE_TMIN
        val deleteRainfall = "DELETE FROM " + AppConstants.TABLE_RAIN_FALL
        db.execSQL(deleteTmax)
        db.execSQL(deleteTmin)
        db.execSQL(deleteRainfall)
    }

    fun insertTmaxList(weatherTmaxList: List<Weather>, placeName: String) {
        val db = this.writableDatabase
        try {
            db.beginTransaction()
            val contentValues = ContentValues()
            for (weather in weatherTmaxList) {
                contentValues.put(AppConstants.KEY_PLACE_NAME, placeName)
                contentValues.put(AppConstants.KEY_VALUE, weather.value)
                contentValues.put(AppConstants.KEY_YEAR, weather.year)
                contentValues.put(AppConstants.KEY_MONTH, weather.month)
                db.insert(AppConstants.TABLE_TMAX, null, contentValues)
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    fun insertTminList(weatherTminList: List<Weather>, placeName: String) {
        val db = this.writableDatabase
        try {
            db.beginTransaction()
            val contentValues = ContentValues()
            for (weather in weatherTminList) {
                contentValues.put(AppConstants.KEY_PLACE_NAME, placeName)
                contentValues.put(AppConstants.KEY_VALUE, weather.value)
                contentValues.put(AppConstants.KEY_YEAR, weather.year)
                contentValues.put(AppConstants.KEY_MONTH, weather.month)
                db.insert(AppConstants.TABLE_TMIN, null, contentValues)
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    fun insertRainfallList(weatherRainfallList: List<Weather>, placeName: String) {
        val db = this.writableDatabase
        try {
            db.beginTransaction()
            val contentValues = ContentValues()
            for (weather in weatherRainfallList) {
                contentValues.put(AppConstants.KEY_PLACE_NAME, placeName)
                contentValues.put(AppConstants.KEY_VALUE, weather.value)
                contentValues.put(AppConstants.KEY_YEAR, weather.year)
                contentValues.put(AppConstants.KEY_MONTH, weather.month)
                db.insert(AppConstants.TABLE_RAIN_FALL, null, contentValues)
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    @SuppressLint("Recycle")
    fun getYearsList(): ArrayList<String> {
        val resultList: ArrayList<String> = ArrayList()
        val db = this.writableDatabase
        val query = "SELECT DISTINCT " + AppConstants.KEY_YEAR + " FROM " + AppConstants.TABLE_TMAX
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                resultList.add(cursor.getString(cursor.getColumnIndex(AppConstants.KEY_YEAR)))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return resultList
    }

    @SuppressLint("Recycle")
    fun getYearWeatherDataList(placesName: String, years: String): ArrayList<Weather> {
        val db = this.writableDatabase
        val query =
            "select r.year,r.month,r.value as rainfall, tmin.value as tmin, tmax.value as tmax from rain_fall r " +
                    "JOIN temp_min tmin ON r.place_name = tmin.place_name AND r.year = tmin.year " +
                    "AND r.month = tmin.month JOIN temp_max tmax ON tmin.place_name = tmax.place_name " +
                    "AND tmin.year = tmax.year AND tmin.month = tmax.month WHERE r.year=" + years + " and r.place_name='" + placesName + "'"
        val weatherList: ArrayList<Weather> = ArrayList()

        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val model = Weather()
                model.year = cursor.getInt(cursor.getColumnIndex(AppConstants.KEY_YEAR))
                model.month = cursor.getInt(cursor.getColumnIndex(AppConstants.KEY_MONTH))
                model.rainFall = cursor.getString(cursor.getColumnIndex(AppConstants.KEY_RAINFALL))
                model.tmax = cursor.getString(cursor.getColumnIndex(AppConstants.KEY_TMAX))
                model.tmin = cursor.getString(cursor.getColumnIndex(AppConstants.KEY_TMIN))
                weatherList.add(model)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return weatherList
    }
}