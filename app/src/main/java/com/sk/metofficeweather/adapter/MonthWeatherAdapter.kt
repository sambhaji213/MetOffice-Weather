package com.sk.metofficeweather.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.sk.metofficeweather.R
import com.sk.metofficeweather.model.Weather
import com.sk.metofficeweather.util.AppAndroidUtils
import com.sk.metofficeweather.util.AppConstants

/*
 * Created by Sambhaji Karad on 08-03-2019
*/

class MonthWeatherAdapter(private val mContext: Context, private val dataList: List<Weather>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = dataList[position]
        val viewHolder = holder as MessageViewHolder

        var tmax = model.tmax
        var tmin = model.tmin
        var rainfall = model.rainFall

        if (tmax.isNullOrEmpty()){
            tmax = AppConstants.NA
        }

        if (tmin.isNullOrEmpty()){
            tmin = AppConstants.NA
        }

        if (rainfall.isNullOrEmpty()){
            rainfall = AppConstants.NA
        }
        viewHolder.textViewMonthName.text = AppAndroidUtils.getMonthForInt(model.month!!)
        val temperature = "$tmax/$tmin"
        viewHolder.textViewTemperature.text = "$temperature " + mContext.resources.getString(R.string.hint_temperature_unit)

        viewHolder.textViewRainFall.text = rainfall+ " " + mContext.resources.getString(R.string.hint_rainfall_unit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootCategoryView = LayoutInflater.from(parent.context).inflate(R.layout.row_month_weather, parent, false)
        return MessageViewHolder(rootCategoryView)
    }

    private inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewMonthName: AppCompatTextView = itemView.findViewById(R.id.textViewMonthName) as AppCompatTextView
        val textViewTemperature: AppCompatTextView =
            itemView.findViewById(R.id.textViewTemperature) as AppCompatTextView
        val textViewRainFall: AppCompatTextView = itemView.findViewById(R.id.textViewRainFall) as AppCompatTextView
    }
}